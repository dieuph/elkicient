FROM docker.elastic.co/elasticsearch/elasticsearch:7.6.0
COPY --chown=elasticsearch:elasticsearch elasticsearch.yml /usr/share/elasticsearch/config/

CMD ["elasticsearch", "-Elogger.level=INFO"]
