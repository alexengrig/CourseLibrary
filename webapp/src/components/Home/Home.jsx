import React, { memo } from 'react';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';

function Home({ title = 'Course Library' }) {
  return (
    <div>
      <h1>{title}</h1>
      <ul>
        <li><NavLink to="/authors">Authors</NavLink></li>
        <li><NavLink to="/publishers">Publishers</NavLink></li>
        <li><NavLink to="/books">Books</NavLink></li>
      </ul>
    </div>
  );
}

Home.propTypes = {
  title: PropTypes.string
};

export default memo(Home);
