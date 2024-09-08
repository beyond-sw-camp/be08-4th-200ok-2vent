FROM nginx:alpine
# dist 폴더 내 파일들을 컨테이너 내의 nginx 서버에 복사
LABEL maintainer="henhen7 <g1008702@gmail.com>"
COPY ./dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80