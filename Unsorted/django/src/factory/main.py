import factory
from src.factory.models import User


class UserFactory(factory.Factory):
    class Meta:
        model = User

    name = 'factory name'
    surname = 'factory surname'
    is_admin = False


print(UserFactory.create())
