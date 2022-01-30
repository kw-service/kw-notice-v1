import firebase_admin
from firebase_admin import credentials
from firebase_admin import messaging

def pushNotification(title, body, url, topic):
    credential_file_path = './FCMServiceAccountKey.json'
    cred = credentials.Certificate(credential_file_path)

    try:
        push_service = firebase_admin.get_app()
    except:
        push_service = firebase_admin.initialize_app(cred)

    message = messaging.Message(
        notification = messaging.Notification(
            title = title,
            body = body
        ),
        android = messaging.AndroidConfig(
            notification = messaging.AndroidNotification(
                click_action = 'FCM_CLICK_ACTION_ACTIVITY'
            )
        ),
        data = {
            'url' : url
        },
        topic = topic
    )

    response = messaging.send(message)