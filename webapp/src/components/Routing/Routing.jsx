import React, { memo } from 'react';
import PropTypes from 'prop-types';
import { HashRouter as DefaultRouter, Redirect, Route, Switch } from 'react-router-dom';

function Routing({ router: Router = DefaultRouter, routes = [] }) {
  return (
    <Router>
      <Switch>
        {routes.map(({ redirect, path, exact, component }, key) => (
          redirect
            ? <Redirect key={key} to={path} exact={exact} />
            : <Route key={key} path={path} exact={exact} component={component} />
        ))}
      </Switch>
    </Router>
  );
}

Routing.propTypes = {
  routes: PropTypes.array
};

export default memo(Routing);
