
# WORKDIR /usr/src/junkstarter/app/frontend
# COPY frontend .
# RUN yarn
# RUN yarn build

# FROM cypress/base:10 as TEST
# COPY package.json .
# RUN yarn install
# RUN yarn test


FROM cypress/base:10 as TEST
# dependencies will be installed only if the package.json file changes
COPY package.json .
RUN yarn install
# copy spec files and website files
COPY cypress cypress
COPY cypress.json .
# rerun E2E tests only if any of the previous files change
RUN yarn cypress:run

# COPY docker-entrypoint.sh /

# ENTRYPOINT [ "/docker-file-host-map.sh" ]