import React, { Fragment } from 'react'
import { render } from 'react-dom'
import { Provider } from 'react-redux'
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { applyMiddleware, createStore } from 'redux'
import { createLogger } from 'redux-logger'
import promiseMiddleware from 'redux-promise-middleware'
import thunkMiddleware from 'redux-thunk'

import HomePage from 'pages/home';
import AboutPage from 'pages/about';

const middleware = [
  thunkMiddleware,
  promiseMiddleware({
    promiseTypeSuffixes: [ 'REQ', 'ACK', 'ERR' ]
  })
];

if (process.env.NODE_ENV !== 'production') {
  middleware.push(createLogger());
}

const store = createStore(
  // reducer,
  applyMiddleware(...middleware)
);

// store.dispatch(fetchAllDummyItems())
// store.dispatch(fetchContainerId())

render(
  <Provider store={store}>
    <Router>
      <Fragment>
        <Route exact path="/" component={HomePage}/>
        <Route path="/about" component={AboutPage}/>
      </Fragment>
    </Router>
  </Provider>,
  document.getElementById('root')
);
