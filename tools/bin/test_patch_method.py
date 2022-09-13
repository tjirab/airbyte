#
# Copyright (c) 2022 Airbyte, Inc., all rights reserved.
#

import requests


def patch_send():
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


def convert_request_to_external_function_input(request: requests.Request):
    body = request.json()
    headers = request.headers()
    query_params = request.params
    return {"body": body, "headers": headers, "params": query_params}


def convert_external_function_output_to_response(output) -> requests.Response:
    response = requests.Response()
    response.status_code = 200
    response._content = output.encode("ascii")  # not sure about this!
    return response


result = requests.get("https://w72cfwmned.execute-api.us-west-1.amazonaws.com/stage").json()

print(result)
