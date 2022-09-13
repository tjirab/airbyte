#
# Copyright (c) 2022 Airbyte, Inc., all rights reserved.
#
import requests


def new_session_send(self, request, **kwargs):
    print(f"request: {request}")
    print(f"kwargs: {kwargs}")
    response = requests.Response()
    response.code = "expired"
    response.error_type = "expired"
    response.status_code = 400
    response._content = b'{ "key" : "a" }'
    return response


requests.sessions.Session.send = new_session_send

result = requests.get("https://w72cfwmned.execute-api.us-west-1.amazonaws.com/stage").json()

print(result)
