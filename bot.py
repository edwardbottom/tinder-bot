import pynder
import robobrowser
import re
import itertools
import self


def get_access_token(email, password):
    MOBILE_USER_AGENT = "Mozilla/5.0 (Linux; U; en-gb; KFTHWI Build/JDQ39) AppleWebKit/535.19 (KHTML, like Gecko) Silk/3.16 Safari/535.19"
    FB_AUTH = "https://www.facebook.com/v2.6/dialog/oauth?redirect_uri=fb464891386855067%3A%2F%2Fauthorize%2F&display=touch&state=%7B%22challenge%22%3A%22IUUkEUqIGud332lfu%252BMJhxL4Wlc%253D%22%2C%220_auth_logger_id%22%3A%2230F06532-A1B9-4B10-BB28-B29956C71AB1%22%2C%22com.facebook.sdk_client_state%22%3Atrue%2C%223_method%22%3A%22sfvc_auth%22%7D&scope=user_birthday%2Cuser_photos%2Cuser_education_history%2Cemail%2Cuser_relationship_details%2Cuser_friends%2Cuser_work_history%2Cuser_likes&response_type=token%2Csigned_request&default_audience=friends&return_scopes=true&auth_type=rerequest&client_id=464891386855067&ret=login&sdk=ios&logger_id=30F06532-A1B9-4B10-BB28-B29956C71AB1&ext=1470840777&hash=AeZqkIcf-NEW6vBd"
    s = robobrowser.RoboBrowser(user_agent=MOBILE_USER_AGENT, parser="lxml")
    s.open(FB_AUTH)
    # submit login form##
    f = s.get_form()
    f["pass"] = password
    f["email"] = email
    s.submit_form(f)
    ##click the 'ok' button on the dialog informing you that you have already authenticated with the Tinder app##
    f = s.get_form()
    s.submit_form(f, submit=f.submit_fields['__CONFIRM__'])
    ##get access token from the html response##
    access_token = re.search(r"access_token=([\w\d]+)", s.response.content.decode()).groups()[0]
    # print(access_token)
    return access_token

def getProfile(session):
    return session.profile

def getMatches(session):
    return session.matches()

def getNearbyUsers(session):
    return session.nearby_users()

def printUserDetails(user):
    print("you matched with ")
    print("name: ", user.name)
    print("bio: ", user.bio)
    print("age: ", user.age)
    print("birth day: ", user.birth_date)
    print("last-online: ", user.ping_time)
    print("distance_km: ", user.distance_km)
    print("connections: ", user.common_connections)
    print("instagram name: ", user.instagram_username)
    print("school: ", user.schools)
    print("job: ", user.jobs)


def getUsers(session):
    return list(session.nearby_users())

def horoscope(user):
    birthday = user.birth_date
    str(birthday)
    return False

def isNotFriend(user):
    list = user.common_connections
    if len(user) > 7:
        return False
    return True

def ageFilter(user):
    if user.age > 25 or user.age < 18:
        return False
    return True

def distanceFiler(user):
    if user.distance_km > 25:
        return False
    return True

def runBot(userName, password, id):
    token = get_access_token()
    id = "100023813949658"
    session = pynder.Session(id, token)
    users = getUsers(session)
    while session.likes_remaining > 0:
        for user in users:
            if isNotFriend(user):
                if ageFilter(user):
                    if distanceFiler(user):
                        if horoscope(user):
                            session.like(user)
                            printUserDetails(user)
                        else:
                            session.dislike(user)
                    else:
                        session.dislike(user)
                else:
                    session.dislike(user)
            else:
                session.dislike(user)
    users = getUsers(session)

def debugSession(session):
    print(session.can_like_in, " can like remaining")
    print(session.likes_remaining, " likes remaining")
    print(session.banned, " banned")



token = get_access_token("sconnors123455555@yahoo.com", "drowssap321")
id = "100023813949658"
session = pynder.Session(id, token)
debugSession(session)











