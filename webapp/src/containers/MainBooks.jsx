import React, { memo, useEffect, useState, useRef } from 'react';
import PropTypes from 'prop-types';
import Books from '../components/Books';
import { NavLink } from 'react-router-dom';

const fetchBooks = condition => fetch('/api/v1/books/search', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(condition)
})
  .then(response => response.json());
const createBook = book => fetch('/api/v1/books', {
  method: 'POST',
  headers: {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
  },
  body: JSON.stringify(book)
});

function MainBooks({
                     title = 'Books',
                     page: initialPage = 0, minPage = 0, maxPage: initialMaxPage = initialPage, pageStep = 1,
                     size: initialSize = 5, minSize = 5, maxSize = 30, sizeStep = 5,
                     books: initialBooks = []
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
  const [books, setBooks] = useState(initialBooks);
  const handleFetchBooks = () => {
    fetchBooks({ page, size })
      .then(({ totalPages, content }) => {
        setMaxPage(totalPages ? totalPages - 1 : minPage);
        setBooks(content);
      })
      .catch(e => {
        console.error('Exception of fetching books', e);
      });
  };
  useEffect(() => {
    handleFetchBooks();
  }, [page, size]);
  const inputBookName = useRef(null);
  const inputBookYear = useRef(null);
  const inputBookAnnotation = useRef(null);
  const inputBookAuthors = useRef(null);
  const inputBookPublisher = useRef(null);
  const inputBookShelf = useRef(null);
  const inputBookShelving = useRef(null);
  const inputBookLibrary = useRef(null);
  const inputBookNote = useRef(null);
  const handleCreateBook = () => {
    const name = inputBookName.current.value;
    const year = inputBookYear.current.value;
    const annotation = inputBookAnnotation.current.value;
    const authors = inputBookAuthors.current.value.split(',').map(s => ({ name: s.trim() }));
    const publisher = ({ name: inputBookPublisher.current.value });
    const shelf = ({ name: inputBookShelf.current.value });
    const shelving = ({ name: inputBookShelving.current.value });
    const library = ({ name: inputBookLibrary.current.value });
    const note = inputBookNote.current.value;
    const location = ({ shelf, shelving, library, note });
    createBook({ name, year, annotation, authors, publisher, location })
      .then(() => handleFetchBooks())
      .catch(e => console.error('Exception of creation book', e));
    inputBookName.current.value = '';
    inputBookYear.current.value = '';
    inputBookAnnotation.current.value = '';
    inputBookAuthors.current.value = '';
    inputBookPublisher.current.value = '';
    inputBookShelf.current.value = '';
    inputBookShelving.current.value = '';
    inputBookLibrary.current.value = '';
    inputBookNote.current.value = '';
  };
  return (
    <div>
      <h1>{title}</h1>
      <ul>
        <li><NavLink to="/home">Home</NavLink></li>
      </ul>
      <h4>Add new book</h4>
      <p><label>Book name: <input ref={inputBookName} /></label></p>
      <p><label>Book year: <input ref={inputBookYear} /></label></p>
      <p><label>Book annotation: <input ref={inputBookAnnotation} /></label></p>
      <p><label>Book authors: <input ref={inputBookAuthors} /></label></p>
      <p><label>Book publisher: <input ref={inputBookPublisher} /></label></p>
      <p><label>Book shelf: <input ref={inputBookShelf} /></label></p>
      <p><label>Book shelving: <input ref={inputBookShelving} /></label></p>
      <p><label>Book library: <input ref={inputBookLibrary} /></label></p>
      <p><label>Book location note: <input ref={inputBookNote} /></label></p>
      <p>
        <button onClick={handleCreateBook}>Add</button>
      </p>
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
      <Books books={books} />
    </div>
  );
}

MainBooks.propTypes = {
  title: PropTypes.string,
  page: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  minPage: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  maxPage: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  pageStep: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  size: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  minSize: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  maxSize: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  sizeStep: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
  books: PropTypes.array
};

export default memo(MainBooks);
