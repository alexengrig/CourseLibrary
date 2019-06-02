import React, { Fragment, memo } from 'react';
import PropTypes from 'prop-types';

const mapPublisher = ({ name }, key) => (
  <li key={key}>{name}</li>
);

function Publishers({ publishers = [] }) {
  return (
    <Fragment>
      {publishers.length ?
        <ul>
          {publishers.map(mapPublisher)}
        </ul> :
        <p>Authors not found</p>
      }
    </Fragment>
  );
}

Publishers.propTypes = {
  publishers: PropTypes.array
};

export default memo(Publishers);
