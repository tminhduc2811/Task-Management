# build stage
FROM node:9.11.1 AS build-stage
WORKDIR /app
COPY package.json ./
RUN npm install
COPY . /app

RUN npm run build

# production stage
FROM nginx:1.17-alpine AS production-stage
COPY --from=build-stage /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
CMD ["nginx", "-g", "daemon off;"]