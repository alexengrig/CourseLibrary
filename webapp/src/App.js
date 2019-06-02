import React from 'react';
import Routing from './components/Routing';

import routes from './routes';
import './assets/css/App.css';

function App() {
  return (
    <Routing routes={routes} />
  );
}

export default App;
