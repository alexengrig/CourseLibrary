import Home from '../components/Home/Home';
import MainAuthors from '../containers/MainAuthors';
import MainPublishers from '../containers/MainPublishers';
import MainBooks from '../containers/MainBooks';

export default [
  { path: '/home', exact: true, component: Home },
  { path: '/authors', exact: true, component: MainAuthors },
  { path: '/publishers', exact: true, component: MainPublishers },
  { path: '/books', exact: true, component: MainBooks },
  { redirect: true, path: '/home', exact: true }
];
