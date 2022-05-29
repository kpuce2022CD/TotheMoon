FROM node:alpine
WORKDIR "/usr/src/app"
COPY package.json ./
RUN yarn install
COPY ./ ./
CMD ["yarn","start"]