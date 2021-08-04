class Users:
	def __init__(self):
		""" Creates a dictionary of users 

				Users structure: 
					{
						idUser: {
								idMovie: rating
								idMovie2: rating
						}
					}
		"""
		
		self.users = {}

	def addUser(self, idUser, idMovie, rating):
		""" Add user to dictionary """

		if idUser in self.users:
			self.users[idUser].append({idMovie: rating}) 

		else:
			ratings = []
			ratings.append({idMovie: rating})
			self.users[idUser] = ratings

	def getUsers(self):
		""" Get users """

		return self.users