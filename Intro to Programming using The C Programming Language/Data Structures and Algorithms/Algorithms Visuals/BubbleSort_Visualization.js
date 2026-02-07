import React, { useState, useEffect } from 'react';
import { Play, RotateCcw, BarChart2, Info, ArrowLeft } from 'lucide-react';

const App = () => {
  const [array, setArray] = useState([]);
  const [sorting, setSorting] = useState(false);
  const [currentIdx, setCurrentIdx] = useState(null);
  const [compareIdx, setCompareIdx] = useState(null);
  const [sortedCount, setSortedCount] = useState(0);
  const [speed, setSpeed] = useState(50);
  const [n, setN] = useState(20);
  const [results, setResults] = useState([]);

  const generateArray = (size = n) => {
    const newArray = Array.from({ length: size }, () => Math.floor(Math.random() * 100) + 10);
    setArray(newArray);
    setSortedCount(0);
    setCurrentIdx(null);
    setCompareIdx(null);
    setSorting(false);
  };

  useEffect(() => {
    generateArray();
  }, [n]);

  const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

  /**
   * CLRS-compliant BUBBLESORT(A, n)
   * 1  for i = 1 to n - 1
   * 2      for j = n downto i + 1
   * 3          if A[j] < A[j - 1]
   * 4              exchange A[j] with A[j - 1]
   */
  const bubbleSort = async () => {
    if (sorting) return;
    setSorting(true);
    
    let arr = [...array];
    let len = arr.length;
    let startTime = performance.now();

    // Line 1: i = 1 to n - 1 (Adjusted to 0 to len - 2 for 0-indexing)
    for (let i = 0; i < len - 1; i++) {
      let swapped = false;
      
      // Line 2: j = n downto i + 1 (Adjusted to len - 1 down to i + 1)
      for (let j = len - 1; j > i; j--) {
        setCurrentIdx(j);
        setCompareIdx(j - 1);
        
        await sleep(101 - speed);

        // Line 3: if A[j] < A[j - 1]
        if (arr[j] < arr[j - 1]) {
          // Line 4: exchange A[j] with A[j - 1]
          let temp = arr[j];
          arr[j] = arr[j - 1];
          arr[j - 1] = temp;
          
          setArray([...arr]);
          swapped = true;
        }
      }
      
      // After inner loop, A[i] is guaranteed to be the minimum of A[i..n]
      setSortedCount(i + 1);
      
      if (!swapped) {
        setSortedCount(len);
        break;
      }
    }
    
    let endTime = performance.now();
    const execTime = (endTime - startTime).toFixed(2);
    setResults(prev => [...prev, { n: len, time: execTime }].slice(-10));
    setSorting(false);
    setCurrentIdx(null);
    setCompareIdx(null);
  };

  return (
    <div className="min-h-screen bg-slate-50 p-4 md:p-8 font-sans text-slate-900">
      <div className="max-w-5xl mx-auto space-y-8">
        <header className="border-b border-slate-200 pb-4">
          <h1 className="text-3xl font-bold tracking-tight text-slate-800">CLRS Bubblesort Visualizer</h1>
          <p className="text-slate-500 mt-1 italic">Implementing Problem 2-2: "Bubble down the minimum"</p>
        </header>

        <div className="grid grid-cols-1 md:grid-cols-3 gap-6 bg-white p-6 rounded-2xl shadow-sm border border-slate-100">
          <div className="space-y-2">
            <label className="text-sm font-semibold uppercase tracking-wider text-slate-400">Array Size (n)</label>
            <input 
              type="range" min="5" max="50" value={n} 
              onChange={(e) => setN(parseInt(e.target.value))}
              disabled={sorting}
              className="w-full h-2 bg-slate-200 rounded-lg appearance-none cursor-pointer accent-indigo-600"
            />
            <div className="flex justify-between text-xs font-mono text-slate-400">
              <span>5</span>
              <span className="text-indigo-600 font-bold">{n}</span>
              <span>50</span>
            </div>
          </div>

          <div className="space-y-2">
            <label className="text-sm font-semibold uppercase tracking-wider text-slate-400">Animation Speed</label>
            <input 
              type="range" min="1" max="100" value={speed} 
              onChange={(e) => setSpeed(parseInt(e.target.value))}
              className="w-full h-2 bg-slate-200 rounded-lg appearance-none cursor-pointer accent-indigo-600"
            />
            <div className="flex justify-between text-xs font-mono text-slate-400">
              <span>Slow</span>
              <span>Fast</span>
            </div>
          </div>

          <div className="flex items-end gap-2">
            <button 
              onClick={bubbleSort}
              disabled={sorting}
              className="flex-1 flex items-center justify-center gap-2 bg-indigo-600 hover:bg-indigo-700 disabled:bg-slate-300 text-white font-semibold py-3 px-4 rounded-xl transition-all shadow-md active:scale-95"
            >
              <Play size={18} /> Run CLRS Logic
            </button>
            <button 
              onClick={() => generateArray()}
              disabled={sorting}
              className="p-3 bg-slate-100 hover:bg-slate-200 text-slate-600 rounded-xl transition-all"
            >
              <RotateCcw size={18} />
            </button>
          </div>
        </div>

        {/* Visualization Area */}
        <div className="bg-white p-8 rounded-2xl shadow-sm border border-slate-100 min-h-[400px] flex items-end justify-center gap-1 overflow-hidden relative">
           {/* Loop Direction Overlay */}
           {sorting && (
             <div className="absolute top-4 right-8 flex items-center gap-2 text-rose-500 font-mono text-sm animate-pulse">
               <ArrowLeft size={16} /> 
               <span>Bubbling minimum to index {sortedCount}</span>
             </div>
           )}
          
          {array.map((value, idx) => {
            // CLRS logic sorts the prefix (beginning of array)
            const isSorted = idx < sortedCount;
            const isComparing = idx === currentIdx || idx === compareIdx;
            
            return (
              <div 
                key={idx}
                className={`w-full rounded-t-lg transition-all duration-150 relative group ${
                  isComparing ? 'bg-rose-500 scale-110 z-10 shadow-lg' : 
                  isSorted ? 'bg-emerald-400' : 'bg-indigo-400'
                }`}
                style={{ 
                  height: `${value * 3}px`,
                  opacity: isSorted ? 1 : (isComparing ? 1 : 0.8)
                }}
              >
                {isComparing && (
                  <div className="absolute -top-12 left-1/2 -translate-x-1/2 flex flex-col items-center">
                    <span className="text-[10px] bg-rose-500 text-white px-1 rounded">j={idx}</span>
                    <div className="w-0.5 h-4 bg-rose-500"></div>
                  </div>
                )}
                <span className="absolute -bottom-6 left-1/2 -translate-x-1/2 text-[10px] font-bold text-slate-400">
                  {idx}
                </span>
              </div>
            );
          })}
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          <div className="bg-slate-900 text-slate-300 p-6 rounded-2xl shadow-lg border border-slate-800">
            <div className="flex items-center gap-2 mb-4">
              <Info className="text-indigo-400" size={20} />
              <h2 className="text-lg font-bold text-white uppercase tracking-tight">CLRS Logic Proof</h2>
            </div>
            <div className="space-y-4 font-mono text-sm">
              <p className="text-xs text-slate-500 italic">"The inner loop invariant: At the start of each iteration, A[j] is the smallest element in A[j..n]."</p>
              <div className="border-l-2 border-emerald-500 pl-4 py-1">
                <p className="text-emerald-400 font-bold mb-1">Sorted Boundary</p>
                <p>A[0...{sortedCount-1}] is sorted.</p>
              </div>
              <div className="border-l-2 border-rose-500 pl-4 py-1">
                <p className="text-rose-400 font-bold mb-1">Current Scan</p>
                <p>j moves from {n-1} down to {sortedCount}.</p>
              </div>
            </div>
          </div>

          <div className="bg-white p-6 rounded-2xl shadow-sm border border-slate-100 flex flex-col">
            <div className="flex items-center gap-2 mb-4">
              <BarChart2 className="text-indigo-600" size={20} />
              <h2 className="text-lg font-bold text-slate-800 uppercase tracking-tight">Experiment Data</h2>
            </div>
            <div className="flex-1 overflow-auto max-h-48">
              <table className="w-full text-left text-sm">
                <thead>
                  <tr className="border-b border-slate-100 text-slate-400 text-[10px] font-bold uppercase tracking-widest">
                    <th className="pb-2">n</th>
                    <th className="pb-2">Time (ms)</th>
                  </tr>
                </thead>
                <tbody>
                  {results.length === 0 ? (
                    <tr><td colSpan="2" className="pt-4 text-center text-slate-300 italic">Run to see growth</td></tr>
                  ) : (
                    results.map((res, i) => (
                      <tr key={i} className="border-b border-slate-50">
                        <td className="py-2 font-mono">{res.n}</td>
                        <td className="py-2 font-mono text-indigo-600 font-bold">{res.time}</td>
                      </tr>
                    ))
                  )}
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default App;