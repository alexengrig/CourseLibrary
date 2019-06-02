import React, { Fragment, memo } from 'react';
import PropTypes from 'prop-types';

const mapAuthor = ({ name }, key) => (
  <li key={key}>{name}</li>
);

function Authors({ authors = [] }) {
  return (
    <Fragment>
      {authors.length ?
        <ul>
          {authors.map(mapAuthor)}
        </ul> :
        <p>Authors not found</p>
      }
    </Fragment>
  );
}

Authors.propTypes = {
  authors: PropTypes.array
};

export default memo(Authors);
