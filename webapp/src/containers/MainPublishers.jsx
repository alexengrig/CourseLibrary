import React, { memo, useEffect, useRef, useState } from 'react';
import PropTypes from 'prop-types';
import { NavLink } from 'react-router-dom';
import Publishers from '../components/Publishers';

const fetchPublishers = condition => fetch('/api/v1/publishers/search', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(condition)
})
  .then(response => response.json());
const createPublisher = name => fetch('/api/v1/publishers', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify({ name })
});

function MainPublisher({
                       title = 'Publishers',
                       page: initialPage = 0, minPage = 0, maxPage: initialMaxPage = initialPage, pageStep = 1,
                       size: initialSize = 5, minSize = 5, maxSize = 30, sizeStep = 5,
                       publishers: initialPublishers = []
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
  const [publishers, setPublishers] = useState(initialPublishers);
  const handleFetchPublishers = () => {
    fetchPublishers({ page, size })
      .then(({ totalPages, content }) => {
        setMaxPage(totalPages ? totalPages - 1 : minPage);
        setPublishers(content);
      })
      .catch(e => {
        console.error('Exception of fetching publishers', e);
      });
  };
  useEffect(() => {
    handleFetchPublishers();
  }, [page, size]);
  const inputEl = useRef(null);
  const handleEnterPress = ({ key }) => {
    if (key === 'Enter') {
      createPublisher(inputEl.current.value)
        .then(() => handleFetchPublishers())
        .catch(e => console.error('Exception of creation publisher', e));
      inputEl.current.value = '';
    }
  };
  return (
    <div>
      <h1>{title}</h1>
      <ul>
        <li><NavLink to="/home">Home</NavLink></li>
      </ul>
      <h4>Add new publisher</h4>
      <label>Publisher name: <input ref={inputEl} onKeyPress={handleEnterPress} /></label>
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
      <Publishers publishers={publishers} />
    </div>
  );
}

MainPublisher.propTypes = {
  title: PropTypes.string,
  page: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  minPage: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  maxPage: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  pageStep: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  size: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  minSize: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  maxSize: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  sizeStep: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  publishers: PropTypes.array
};

export default memo(MainPublisher);
