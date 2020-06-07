import React from 'react';
import './App.css';
import Layout from './Components/Layout/Layout';
import Dashboard from './Containers/Dashboard/Dashboard';

function App() {
  return (
    <div className="App">
      <Layout>
        <Dashboard></Dashboard>
      </Layout>
    </div>
  );
}

export default App;
