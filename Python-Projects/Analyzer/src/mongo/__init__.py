from pymongo import MongoClient

client = MongoClient("localhost", 27017) # TODO replace with env variable
db = client.sms
smsCollection = db.sms
