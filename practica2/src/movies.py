class Movies:
	def __init__(self):
		""" Creates a dictionary of movies 

				Movies structure: 
					{
						idMovie: title
					}
		"""

		self.movies = {}

	def addMovie(self, idMovie, title):
		""" Add movie to dictionary """

		self.movies[idMovie] = title

	def getMovies(self):
		""" Get movies """
		
		return self.movies