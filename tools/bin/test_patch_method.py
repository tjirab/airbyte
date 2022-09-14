#
# Copyright (c) 2022 Airbyte, Inc., all rights reserved.
#

import requests


def patch_send(session):
    import requests

    def convert_request_to_external_function_input(request: requests.PreparedRequest):
        body = request.body
        headers = request.headers
        url = request.url
        return {"body": body, "headers": headers, "url": url}

    def convert_external_function_output_to_response(output) -> requests.Response:
        response = requests.Response()
        response.status_code = 200
        actual_output = output[0].as_dict()["ALEX_TEST_DB.PUBLIC.TEST_EXTERNAL_FUNCTION()"]
        response._content = actual_output.encode("ascii")  # not sure about this!
        return response

    def new_session_send(self, request, **kwargs):
        # convert to external function arguments
        # args = convert_request_to_external_function_input(request)

        # call external function
        if session:
            # FIXME: pass the args to the external function...
            output_from_external_function = session.sql("select ALEX_TEST_DB.PUBLIC.test_external_function();").collect()
            response = convert_external_function_output_to_response(output_from_external_function)
        else:
            content = b'{"data": "hello"}'
            response = requests.Response()
            response.status_code = 200
            response._content = content
        return response

    requests.sessions.Session.send = new_session_send


patch_send(None)
result = requests.get("https://w72cfwmned.execute-api.us-west-1.amazonaws.com/stage").json()
print(result)
