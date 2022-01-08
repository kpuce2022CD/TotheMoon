from flask import Flask, request, jsonify
from comment import collectComment

def create_app():
  app = Flask(__name__)
  app.config['JSON_AS_ASCII'] = False

  @app.route('/', methods=['GET'])
  def start():
    return jsonify(collectComment(request.args.get('url')))
  
  return app