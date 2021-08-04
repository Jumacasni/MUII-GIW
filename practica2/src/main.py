from users import Users
from movies import Movies
from recommendation_system import RecommendationSystem
from random import randint

def main():

	users_filepath = ('u.data')
	movies_filepath = ('u.item')

	# Parse file users
	users = parseUsers(users_filepath)

	# Parse file movies
	movies = parseMovies(movies_filepath)

	print("*************************\nSISTEMA DE RECOMENDACIÓN\n*************************")
	print("\nValora las siguientes películas con una puntuación del 1 al 5.\n")

	# Get ratings from user input
	chosen_movies = []
	for i in range(20):
		random_movie = randint(1, len(movies.getMovies()))
		# Prevent repeating movies
		while(random_movie in chosen_movies):
			random_movie = randint(1, len(movies.getMovies()))

		print(str(i+1)+". Valora la película: "+movies.getMovies()[random_movie])
		chosen_movies.append(random_movie)

		# Accept only an integer in range [1-5]
		rating = input("Introduce tu valoración (1-5): ")
		while not isInt(rating) or int(rating) < 1 or int(rating) > 5:
			rating = input("Introduce tu valoración (1-5): ")

		# Add user rating to users 
		users.addUser(0, random_movie, int(rating))

		print("\n")

	rs = RecommendationSystem(users, movies)

	# Calculate similarity with other users
	rs.calculateSimilarity()
	# Calculate best k neighbours
	rs.calculateNeighborhood(10)
	print("\n")
	# Get best recommendations
	recommendations = rs.getRecommendations()
	print("\n")

	# Visualize recommendations
	answer = input("Hay "+str(len(recommendations))+" películas recomendadas para ti, ¿quieres verlas? (s/n):\n")
	if answer == "s" or answer == "S":
		print("\nRecomendaciones:\n")
		for index, r in enumerate(recommendations):
			idMovie = r[0]
			rating = r[1]
			movie = movies.getMovies()[idMovie]
			print(str(index)+". "+movie+" --> Valoración: "+str(round(rating,2))+"\n")

def parseUsers(file):
	""" Parse file u.data and create users """

	users = Users()

	with open(file, 'r', encoding='latin-1') as file:
		line = file.readline()

		while line:
			split_line = line.split('\t')
			users.addUser(int(split_line[0]), int(split_line[1]), int(split_line[2]))

			line = file.readline()

		file.close()

	return users

def parseMovies(file):
	""" Parse file u.item and create movies """
	movies = Movies()

	with open(file, 'r', encoding='latin-1') as file:
		line = file.readline()

		while line:
			split_line = line.split('|')
			movies.addMovie(int(split_line[0]), split_line[1])

			line = file.readline()

		file.close()

	return movies

def isInt(s):
	""" Check if s is int """
	try: 
		int(s)
		return True
	except ValueError:
		return False

if __name__ == "__main__":
	main()