from flask import Flask, request, jsonify
from comment import collectComment
from np_classify import npClassifyProcessing,\
    comment_classify,sentiment_predict,remove_emoji,emoticonToWord



def create_app():
    app = Flask(__name__)

    @app.route('/', methods=['GET'])
    def start():
        collectComment(request.args.get('url'))
        return "success"

    @app.route('/tospring')
    def test():
        array = ["1", "a", "3", "j", "1"]

        return jsonify(array)

    @app.route('/tospring2')
    def test2():
        filepath = collectComment(request.args.get('url'))
        comment_data = npClassifyProcessing(filepath)

        return jsonify(comment_data)

    return app
