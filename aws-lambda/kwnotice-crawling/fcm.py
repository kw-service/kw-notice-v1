import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging

def pushNotification(title, body, topic):
    credential_file_path = 'kwnotice-crawling/FCMServiceAccountKey.json'
    cred = credentials.Certificate(credential_file_path)
    
    try:
        push_service = firebase_admin.get_app()
    except:
        push_service = firebase_admin.initialize_app(cred)

    message = messaging.Message(
        data = {
            'title' : title,
            'body' : body
        },
        notification = messaging.Notification(title = title, body = body),
        topic = topic
    )

    response = messaging.send(message)