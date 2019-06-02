import React, { Fragment, memo } from 'react';
import PropTypes from 'prop-types';

const mapBook = ({
                   name = 'none', annotation = 'none', year = 'none',
                   authors = [], publisher = {}, location = {}
                 }, key) => {
  const authorNames = authors.length ?
    authors.map(({ name }) => name).join(', ') :
    'none';
  const { name: publisherName = 'none' } = publisher ? publisher : {};
  const { shelf = {}, shelving = {}, library = {}, note = 'none' } = location ? location : {};
  const { name: shelfName = 'none' } = shelf ? shelf : {};
  const { name: shelvingName = 'none' } = shelving ? shelving : {};
  const { name: libraryName = 'none' } = library ? library : {};
  return (
    <li key={key}>
      <p><b>Name:</b> {name}</p>
      <p><b>Year:</b> {year}</p>
      <p><b>Annotation:</b> {annotation}</p>
      <p><b>Authors:</b> {authorNames}</p>
      <p><b>Publisher:</b> {publisherName}</p>
      <p><b>Location:</b> {`Library: ${libraryName}; shelving: ${shelvingName}; shelf: ${shelfName}; note: ${note}`}</p>
    </li>
  );
};

function Books({ books = [] }) {
  return (
    <Fragment>
      {books.length ?
        <ul>
          {books.map(mapBook)}
        </ul> :
        <p>Authors not found</p>
      }
    </Fragment>
  );
}

Books.propTypes = {
  books: PropTypes.array
};

export default memo(Books);
