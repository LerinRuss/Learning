FROM continuumio/miniconda3

RUN mkdir "app"
WORKDIR app

COPY . .

RUN conda env create  --name App -f environment.yml
CMD conda run -n App --no-capture-output python manage.py runserver 0.0.0.0:8000
