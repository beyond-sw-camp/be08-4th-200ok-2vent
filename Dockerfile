FROM nginx:alpine 
LABEL maintainer="henhen7 <g1008702@gmail.com>"
COPY frontend/dist /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80