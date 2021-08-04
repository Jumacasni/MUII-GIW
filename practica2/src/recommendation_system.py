import numpy as np
from math import sqrt
from progress.bar import ChargingBar

class RecommendationSystem:
	def __init__(self, users, movies):
		""" Constructor receives users and movies """

		self.users = users
		self.movies = movies

	def createRatingMatrix(self):
		""" Creates a matrix: rows are users and columns are movies, matrix[row][column] is rating """

		# Movies not rated by user are NaN
		self.matrix = np.empty((len(self.users.getUsers()), len(self.movies.getMovies())))
		self.matrix[:] = np.nan

		for user in self.users.getUsers():
			for i in range(len(self.users.getUsers()[user])):
				idUser = user
				idMovie = list(self.users.getUsers()[user][i].keys())[0]
				rating = list(self.users.getUsers()[user][i].values())[0]
				
				self.matrix[idUser][idMovie-1] = rating

	def calculateSimilarity(self):
		""" Calculate similarity using pearson. It uses cosine similarity in case pearson's denominator is zero """

		# Create rating matrix
		self.createRatingMatrix()
		
		n_users = self.matrix.shape[0]
		my_user_ratings = self.matrix[0]
		my_avg = np.nanmean(self.matrix[0], axis=0)
		similarity_matrix = []

		# Obtain all common movies between user and other users and calculate similarity with them
		bar = ChargingBar('Calculando similaridad', max=n_users-1)
		for user in range(1, n_users):
			user_ratings = self.matrix[user]
			user_avg = np.nanmean(user_ratings)

			common_movies = []

			for movie, my_rating in enumerate(my_user_ratings):
				if not np.isnan(my_rating):
					user_rating = self.matrix[user][movie]
					if not np.isnan(user_rating):
						common_movies.append(movie)

			# Continue to next user if not movies in common
			if len(common_movies) == 0:
				bar.next()
				continue

			# Get pearson correlation
			sim = self.pearsonCorrelation(common_movies, my_user_ratings, user_ratings, my_avg, user_avg)

			# If pearson's denominator is zero, use cosine similarity
			if sim is None:
				sim = self.cosine(common_movies, my_user_ratings, user_ratings)

			# Just in case it returns 1.0000000002...
			if sim > 1:
				sim = 1.0

			similarity_matrix.append([user,sim])

			bar.next()

		bar.finish()

		# Sort similarity matrix starting from best similarities
		self.similarity_matrix = sorted(similarity_matrix,key=lambda x: x[1], reverse=True)

	def calculateNeighborhood(self, k):
		""" Create neighborhood selecing k best similarities """

		self.neighborhood = []
		for i in range(k):
			self.neighborhood.append([self.similarity_matrix[i][0],self.similarity_matrix[i][1]])

	def getRecommendations(self):
		""" Get recommendations based on similarity with other users """
		my_user_ratings = self.matrix[0]
		recommendations = []

		bar = ChargingBar('Obteniendo recomendaciones', max=len(my_user_ratings))
		# Rate a movie which is not rated by user according to neighbours' ratings
		for movie, my_rating in enumerate(my_user_ratings):
			numerator = 0
			denominator = 0
			if np.isnan(my_rating):
				for user in self.neighborhood:
					idUser = user[0]
					similarity = user[1]
					user_rating = self.matrix[idUser][movie]

					if not np.isnan(user_rating):
						numerator += similarity*user_rating
						denominator += similarity

			if not denominator == 0:
				rating = numerator/denominator

				# Recommedante movie only if rating >= 4
				# movie+1 because id movies start at 1
				if rating >= 4:
					recommendations.append([movie+1, rating])

			bar.next()

		bar.finish()
		recommendations = sorted(recommendations,key=lambda x: x[1], reverse=True)
		
		return recommendations

	def pearsonCorrelation(self, common_movies, my_user_ratings, user_ratings, my_avg, user_avg):
		""" Calculate pearson correlation. It normalizes the result because if denominator is zero, then cosine similarity is used """

		numerator = 0
		denominator1 = 0
		denominator2 = 0

		for movie in common_movies:
			numerator += (my_user_ratings[movie]-my_avg)*(user_ratings[movie]-user_avg)
			denominator1 += pow((my_user_ratings[movie]-my_avg),2)
			denominator2 += pow((user_ratings[movie]-user_avg),2)
		
		denominator = sqrt(denominator1)*sqrt(denominator2)

		if denominator == 0:
			return None

		else:
			sim = numerator/denominator
			sim_normalized = (sim - (-1)) / 2

			return sim_normalized

	def cosine(self, common_movies, my_user_ratings, user_ratings):
		""" Calculate cosine similarity. Only used when pearson's denominator is zero """

		numerator = 0
		denominator1 = 0
		denominator2 = 0

		for movie in common_movies:
			numerator += my_user_ratings[movie]*user_ratings[movie]
			denominator1 += pow(my_user_ratings[movie],2)
			denominator2 += pow(user_ratings[movie],2)
		
		denominator = sqrt(denominator1)*sqrt(denominator2)

		return numerator/denominator