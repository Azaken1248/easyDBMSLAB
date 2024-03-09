from flask import Flask, jsonify, request
from databaseMYSQLver import connection,queryMode
import json


def toJson(header, data):
    json_data = {}

    # Add header tuple to JSON data
    header_key = header[0]
    json_data[header_key] = []

    for tup in data:
        value = tup[0]
        json_data[header_key].append(value)
        
    print("returned : ",json_data)
    return json_data


app = Flask(__name__)



@app.route('/data', methods=['GET', 'POST'])
def handle_data():
    if request.method == 'POST':
        req_data = request.get_json()
        print("Received data:", req_data)
      
        creds = 'credentials.json'
        query = "queries.json"
        
        if("host" in req_data.keys()):
            with open(creds, 'w') as json_file:
                json.dump(req_data, json_file, indent=4)
        if("query" in req_data.keys()):
            with open(query, 'w') as json_file:
                json.dump(req_data,json_file,indent=4)
        
        
        return jsonify({"message": "Data received successfully"})
    
    elif request.method == 'GET':
        print("IN GET")
        creds = open('credentials.json','r')
        credentials = json.load(creds)
        
        queryFile = open('queries.json','r')
        queries = json.load(queryFile)
        
        
        con = None
        
        try:
            con = connection(credentials['host'],credentials['user'],credentials['passwprd'])
        except KeyError:
            print("Can't Connect Without Credentials!")
        
        cursor = con.cursor()
        
        if(queries["query"]):
            try:
                cursor.execute(queries["query"])
                resultSet = cursor.fetchall()
            except:
                pass
        header = [desc[0] for desc in cursor.description]    
        resultSet = toJson(header,resultSet)
        
        return jsonify(resultSet)

if __name__ == '__main__':
    app.run(debug=True)
