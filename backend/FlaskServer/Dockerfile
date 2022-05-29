FROM python:3.9.8

WORKDIR "/usr/src/app"

COPY . .
ENV FLASK_ENV=development
ENV FLASK_APP=app
RUN apt-get update
RUN apt-get -y install gcc
RUN apt-get -y install default-jdk -y
RUN pip install --upgrade requests
RUN pip install --upgrade pip
RUN pip install --upgrade --no-deps --force-reinstall -r requirements.txt

CMD ["python","-m", "flask", "run", "--host=0.0.0.0"]