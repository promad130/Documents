import React, { useState, useEffect } from 'react';
import { Play, RotateCcw, BarChart2, Info, ArrowDown, GitGraph, FileJson, TrendingUp } from 'lucide-react';

const App = () => {
  const [array, setArray] = useState([]);
  const [sorting, setSorting] = useState(false);
  const [currentIdx, setCurrentIdx] = useState(null);
  const [keyIdx, setKeyIdx] = useState(null);
  const [keyValue, setKeyValue] = useState(null);
  const [sortedCount, setSortedCount] = useState(1);
  const [speed, setSpeed] = useState(80);
  const [n, setN] = useState(20);
  const [results, setResults] = useState([]);
  const [view, setView] = useState('visualizer'); // 'visualizer' | 'analysis'

  const generateArray = (size = n) => {
    const newArray = Array.from({ length: size }, () => Math.floor(Math.random() * 100) + 10);
    setArray(newArray);
    setSortedCount(1);
    setCurrentIdx(null);
    setKeyIdx(null);
    setKeyValue(null);
    setSorting(false);
  };

  useEffect(() => {
    generateArray();
  }, [n]);

  const sleep = (ms) => new Promise((resolve) => setTimeout(resolve, ms));

  const runBenchmark = async () => {
    const benchmarkResults = [];
    for (let size = 5; size <= 100; size += 5) {
      const testArr = Array.from({ length: size }, () => Math.floor(Math.random() * 1000));
      const start = performance.now();
      
      // Inline insertion sort for benchmarking
      let arr = [...testArr];
      for (let j = 1; j < arr.length; j++) {
        let key = arr[j];
        let i = j - 1;
        while (i >= 0 && arr[i] > key) {
          arr[i + 1] = arr[i];
          i--;
        }
        arr[i + 1] = key;
      }
      
      const end = performance.now();
      benchmarkResults.push({ n: size, time: (end - start).toFixed(4) });
    }
    setResults(benchmarkResults);
    setView('analysis');
  };

  const insertionSort = async () => {
    if (sorting) return;
    setSorting(true);
    let arr = [...array];
    let len = arr.length;
    for (let j = 1; j < len; j++) {
      let key = arr[j];
      setKeyIdx(j);
      setKeyValue(key);
      await sleep(101 - speed);
      let i = j - 1;
      while (i >= 0 && arr[i] > key) {
        setCurrentIdx(i);
        await sleep(101 - speed);
        arr[i + 1] = arr[i];
        setArray([...arr]);
        i = i - 1;
      }
      arr[i + 1] = key;
      setArray([...arr]);
      setSortedCount(j + 1);
      setCurrentIdx(null);
      setKeyValue(null);
      await sleep(101 - speed);
    }
    setSorting(false);
  };

  const downloadCSV = () => {
    const csvRows = [["n", "time_ms"], ...results.map(r => [r.n, r.time])];
    const csvContent = csvRows.map(e => e.join(",")).join("\n");
    const blob = new Blob([csvContent], { type: 'text/csv' });
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.setAttribute('hidden', '');
    a.setAttribute('href', url);
    a.setAttribute('download', 'insertion_sort_results.csv');
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  };

  return (
    <div className="min-h-screen bg-slate-50 p-4 md:p-8 font-sans text-slate-900">
      <div className="max-w-6xl mx-auto space-y-6">
        <header className="flex flex-col md:flex-row justify-between items-start md:items-center border-b border-slate-200 pb-4 gap-4">
          <div>
            <h1 className="text-3xl font-bold tracking-tight text-slate-800">Insertion Sort Laboratory</h1>
            <p className="text-slate-500 mt-1 italic">CS F211 Experimental Analysis (2026)</p>
          </div>
          <div className="flex bg-white rounded-lg p-1 shadow-sm border border-slate-200">
            <button 
              onClick={() => setView('visualizer')}
              className={`px-4 py-2 rounded-md text-sm font-semibold transition-all ${view === 'visualizer' ? 'bg-amber-500 text-white shadow-sm' : 'text-slate-500 hover:bg-slate-50'}`}
            >
              Visualizer
            </button>
            <button 
              onClick={() => setView('analysis')}
              className={`px-4 py-2 rounded-md text-sm font-semibold transition-all ${view === 'analysis' ? 'bg-amber-500 text-white shadow-sm' : 'text-slate-500 hover:bg-slate-50'}`}
            >
              Experimental Data
            </button>
          </div>
        </header>

        {view === 'visualizer' ? (
          <>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6 bg-white p-6 rounded-2xl shadow-sm border border-slate-100">
              <div className="space-y-2">
                <label className="text-sm font-semibold text-slate-400 uppercase tracking-wider">Input Size</label>
                <input type="range" min="5" max="50" value={n} onChange={(e) => setN(parseInt(e.target.value))} disabled={sorting} className="w-full accent-amber-500" />
                <div className="flex justify-between text-xs font-mono text-slate-400"><span>5</span><span className="text-amber-600">{n}</span><span>50</span></div>
              </div>
              <div className="space-y-2">
                <label className="text-sm font-semibold text-slate-400 uppercase tracking-wider">Speed</label>
                <input type="range" min="1" max="100" value={speed} onChange={(e) => setSpeed(parseInt(e.target.value))} className="w-full accent-amber-500" />
              </div>
              <div className="flex items-end gap-2">
                <button onClick={insertionSort} disabled={sorting} className="flex-1 bg-amber-500 hover:bg-amber-600 text-white font-bold py-3 px-4 rounded-xl transition-all shadow-md active:scale-95 flex items-center justify-center gap-2"><Play size={18} /> Sort</button>
                <button onClick={() => generateArray()} disabled={sorting} className="p-3 bg-slate-100 text-slate-600 rounded-xl hover:bg-slate-200"><RotateCcw size={18} /></button>
              </div>
            </div>

            <div className="bg-white p-8 rounded-2xl shadow-sm border border-slate-100 min-h-[450px] flex items-end justify-center gap-1 relative overflow-hidden">
               <div className="absolute top-6 left-8 flex flex-col gap-2">
                <div className="flex items-center gap-2 text-xs font-bold text-slate-400 uppercase tracking-widest"><GitGraph size={14}/> Flow Logic:</div>
                <div className={`p-2 rounded text-xs border ${keyValue ? 'border-amber-200 bg-amber-50 text-amber-700' : 'border-slate-100 bg-slate-50 text-slate-400'}`}>Extract Key: A[{keyIdx}] = {keyValue || '?'}</div>
                <div className={`p-2 rounded text-xs border ${currentIdx !== null ? 'border-rose-200 bg-rose-50 text-rose-700' : 'border-slate-100 bg-slate-50 text-slate-400'}`}>Shift: A[{currentIdx}] &gt; {keyValue || '?'}</div>
              </div>

              {array.map((value, idx) => {
                const isKey = idx === keyIdx;
                const isComparing = idx === currentIdx;
                const isSorted = idx < sortedCount;
                return (
                  <div key={idx} className={`w-full rounded-t transition-all duration-150 relative ${isKey ? 'bg-amber-500 scale-105 z-10' : isComparing ? 'bg-rose-500 scale-110' : isSorted ? 'bg-indigo-400' : 'bg-slate-200'}`} style={{ height: `${value * 3}px` }}>
                    {isKey && <div className="absolute -top-6 left-1/2 -translate-x-1/2 text-[10px] font-bold text-amber-600">KEY</div>}
                    <span className="absolute -bottom-6 left-1/2 -translate-x-1/2 text-[9px] font-mono text-slate-400">{value}</span>
                  </div>
                );
              })}
            </div>
          </>
        ) : (
          <div className="grid grid-cols-1 md:grid-cols-2 gap-8 animate-in fade-in slide-in-from-bottom-4 duration-500">
            <div className="bg-white p-6 rounded-2xl border border-slate-200 shadow-sm flex flex-col">
              <div className="flex justify-between items-center mb-6">
                <h3 className="text-lg font-bold text-slate-800 flex items-center gap-2"><TrendingUp size={20} className="text-amber-500"/> Runtime Complexity ($\Theta(n^2)$)</h3>
                <button onClick={runBenchmark} className="text-xs bg-indigo-50 text-indigo-600 px-3 py-1 rounded-full font-bold hover:bg-indigo-100">Run Experiments</button>
              </div>
              
              <div className="flex-1 min-h-[300px] flex items-end gap-1 border-b border-l border-slate-100 p-2">
                {results.map((r, i) => (
                  <div key={i} className="flex-1 bg-amber-400 hover:bg-amber-500 transition-all rounded-t relative group" style={{ height: `${Math.min(r.time * 500, 280)}px` }}>
                    <div className="absolute -top-10 left-1/2 -translate-x-1/2 bg-slate-800 text-white text-[10px] p-1 rounded opacity-0 group-hover:opacity-100 transition-opacity z-20">
                      n={r.n}<br/>{r.time}ms
                    </div>
                  </div>
                ))}
              </div>
              <div className="flex justify-between mt-2 text-[10px] font-bold text-slate-400 uppercase tracking-widest">
                <span>n = 5</span>
                <span>Size of Array</span>
                <span>n = 100</span>
              </div>
            </div>

            <div className="bg-slate-900 text-slate-300 p-6 rounded-2xl shadow-xl space-y-6">
              <h3 className="text-lg font-bold text-white flex items-center gap-2"><FileJson size={20} className="text-amber-400"/> Dataset Export</h3>
              <p className="text-sm leading-relaxed text-slate-400">Generate random datasets and measure performance according to the academic standards of the CS F211 labs.</p>
              
              <div className="bg-slate-800 rounded-xl p-4 font-mono text-xs text-amber-400 border border-slate-700">
                {results.slice(0, 5).map((r, i) => (
                  <div key={i}>{r.n}, {r.time}</div>
                ))}
                {results.length > 5 && <div>...</div>}
                {results.length === 0 && <div className="text-slate-600 italic">// Run benchmark to populate csv</div>}
              </div>

              <button 
                onClick={downloadCSV}
                disabled={results.length === 0}
                className="w-full py-3 bg-white hover:bg-slate-100 text-slate-900 font-bold rounded-xl disabled:bg-slate-700 disabled:text-slate-500 transition-all"
              >
                Download .CSV Results
              </button>
            </div>
          </div>
        )}

        <footer className="bg-slate-100 p-6 rounded-2xl border border-slate-200">
          <div className="flex items-start gap-4">
            <Info className="text-amber-600 mt-1" size={20} />
            <div className="space-y-1">
              <p className="text-sm font-bold text-slate-700">Academic Note:</p>
              <p className="text-xs text-slate-600 leading-relaxed">
                Insertion Sort is stable and in-place. Its average running time is $\Theta(n^2)$, but it outperforms $O(n \lg n)$ algorithms for very small $n$ because of its low constant factors. This is why many library implementations of `std::sort` switch to Insertion Sort once the recursive sub-arrays become small.
              </p>
            </div>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default App;