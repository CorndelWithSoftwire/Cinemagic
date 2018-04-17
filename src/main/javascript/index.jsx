import * as React from 'react';
import * as ReactDOM from 'react-dom';

import App from './components/app';
import ErrorBoundary from './components/errorBoundary';

ReactDOM.render(<ErrorBoundary><App /></ErrorBoundary>, document.getElementById('react-root'));
