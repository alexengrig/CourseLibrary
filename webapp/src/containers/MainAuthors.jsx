import React, { memo, useEffect, useState, useRef } from 'react';
import PropTypes from 'prop-types';
import Authors from '../components/Authors';
import { NavLink } from 'react-router-dom';

const fetchAuthors = condition => fetch('/api/v1/authors/search', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(condition)
})
  .then(response => response.json());
const createAuthor = name => fetch('/api/v1/authors', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({ name })
});

function MainAuthors({
                       title = 'Authors',
                       page: initialPage = 0, minPage = 0, maxPage: initialMaxPage = initialPage, pageStep = 1,
                       size: initialSize = 5, minSize = 5, maxSize = 30, sizeStep = 5,
                       authors: initialAuthors = []
                     }) {
  const [maxPage, setMaxPage] = useState(initialMaxPage);
  const [page, setPage] = useState(initialPage);
  const handleNextPage = () => {
    page + pageStep <= maxPage && setPage(page + pageStep);
  };
  const handlePrevPage = () => {
    page - pageStep >= minPage && setPage(page - pageStep);
  };
  const [size, setSize] = useState(initialSize);
  const handleIncSize = () => {
    size + sizeStep <= maxSize && setSize(size + sizeStep);
  };
  const handleDecSize = () => {
    size - sizeStep >= minSize && setSize(size - sizeStep);
  };
  const [authors, setAuthors] = useState(initialAuthors);
  const handleFetchAuthors = () => {
    fetchAuthors({ page, size })
      .then(({ totalPages, content }) => {
        setMaxPage(totalPages ? totalPages - 1 : minPage);
        setAuthors(content);
      })
      .catch(e => {
        console.error('Exception of fetching authors', e);
      });
  };
  useEffect(() => {
    handleFetchAuthors();
  }, [page, size]);
  const inputEl = useRef(null);
  const handleEnterPress = ({ key }) => {
    if (key === 'Enter') {
      createAuthor(inputEl.current.value)
        .then(() => handleFetchAuthors())
        .catch(e => console.error('Exception of creation author', e));
      inputEl.current.value = '';
    }
  };
  return (
    <div>
      <h1>{title}</h1>
      <ul>
        <li><NavLink to="/home">Home</NavLink></li>
      </ul>
      <h4>Add new author</h4>
      <label>Author name: <input ref={inputEl} onKeyPress={handleEnterPress} /></label>
      <p>
        <button onClick={handlePrevPage}>-</button>
        <span> Page: {page + 1} </span>
        <button onClick={handleNextPage}>+</button>
      </p>
      <p>
        <button onClick={handleDecSize}>-</button>
        <span> Size: {size} </span>
        <button onClick={handleIncSize}>+</button>
      </p>
      <Authors authors={authors} />
    </div>
  );
}

MainAuthors.propTypes = {
  title: PropTypes.string,
  page: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  minPage: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  maxPage: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  pageStep: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  size: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  minSize: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  maxSize: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  sizeStep: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  authors: PropTypes.array
};

export default memo(MainAuthors);
