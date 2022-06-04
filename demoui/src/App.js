import './App.css';
import HomeCard from './components/HomeCard';

function App() {
  return (
    <div className="App">
      <div className="relative flex min-h-screen flex-col justify-center overflow-hidden bg-gray-50 py-6 sm:py-12">
        <HomeCard />
      </div>
    </div>
  );
}

export default App;
