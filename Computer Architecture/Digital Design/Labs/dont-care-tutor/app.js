// Application state
let currentKMap = null;
let selectedCells = [];
let quizAnswers = [];

// Quiz questions data
const quizQuestions = [
    {
        question: "What symbol is typically used to represent don't care conditions in K-maps?",
        options: ["0", "1", "X", "?"],
        correct: 2
    },
    {
        question: "Why are don't care conditions useful in circuit design?",
        options: ["They make circuits more complex", "They allow for simpler Boolean expressions", "They increase power consumption", "They are required by law"],
        correct: 1
    },
    {
        question: "In BCD encoding, which decimal values create don't care conditions?",
        options: ["0-9", "10-15", "0-15", "8-9"],
        correct: 1
    }
];

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    initializeNavigation();
    initializeKMap();
    initializeQuiz();
    
    // Set tutorial as active section by default
    showSection('tutorial');
});

// Navigation functionality
function initializeNavigation() {
    const navButtons = document.querySelectorAll('.nav-btn');
    
    navButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            const sectionName = this.getAttribute('data-section');
            console.log('Switching to section:', sectionName); // Debug log
            showSection(sectionName);
            
            // Update active button
            navButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
        });
    });
}

function showSection(sectionName) {
    console.log('showSection called with:', sectionName); // Debug log
    
    // Hide all sections
    const allSections = document.querySelectorAll('.section');
    allSections.forEach(section => {
        section.classList.remove('active');
        console.log('Hiding section:', section.id); // Debug log
    });
    
    // Show selected section
    const targetSection = document.getElementById(sectionName);
    if (targetSection) {
        targetSection.classList.add('active');
        console.log('Showing section:', targetSection.id); // Debug log
    } else {
        console.error('Section not found:', sectionName); // Debug log
    }
    
    // Update navigation buttons
    document.querySelectorAll('.nav-btn').forEach(btn => {
        btn.classList.remove('active');
        if (btn.getAttribute('data-section') === sectionName) {
            btn.classList.add('active');
        }
    });
}

// K-Map functionality
function initializeKMap() {
    const generateBtn = document.getElementById('generateKmap');
    const clearBtn = document.getElementById('clearGroups');
    
    if (generateBtn) {
        generateBtn.addEventListener('click', generateKMap);
    }
    
    if (clearBtn) {
        clearBtn.addEventListener('click', clearGroupings);
    }
    
    // Generate initial K-map with a small delay to ensure DOM is ready
    setTimeout(() => {
        generateKMap();
    }, 100);
}

function generateKMap() {
    const variableCountEl = document.getElementById('variableCount');
    const mintermsEl = document.getElementById('minterms');
    const dontCaresEl = document.getElementById('dontCares');
    
    if (!variableCountEl || !mintermsEl || !dontCaresEl) {
        console.log('K-map elements not ready yet');
        return;
    }
    
    const variableCount = parseInt(variableCountEl.value);
    const minterms = parseNumbers(mintermsEl.value);
    const dontCares = parseNumbers(dontCaresEl.value);
    
    currentKMap = {
        variables: variableCount,
        minterms: minterms,
        dontCares: dontCares
    };
    
    createKMapGrid(variableCount, minterms, dontCares);
    selectedCells = [];
    updateResults();
}

function parseNumbers(input) {
    if (!input || !input.trim()) return [];
    return input.split(',').map(n => parseInt(n.trim())).filter(n => !isNaN(n));
}

function createKMapGrid(variables, minterms, dontCares) {
    const grid = document.getElementById('kmapGrid');
    if (!grid) return;
    
    grid.innerHTML = '';
    
    if (variables === 3) {
        grid.className = 'kmap-grid grid-3var';
        create3VarKMap(grid, minterms, dontCares);
    } else {
        grid.className = 'kmap-grid grid-4var';
        create4VarKMap(grid, minterms, dontCares);
    }
}

function create4VarKMap(grid, minterms, dontCares) {
    // Gray code ordering for 4-variable K-map
    const cellOrder = [
        0, 1, 3, 2,    // AB=00
        4, 5, 7, 6,    // AB=01
        12, 13, 15, 14, // AB=11
        8, 9, 11, 10   // AB=10
    ];
    
    for (let i = 0; i < 16; i++) {
        const cell = document.createElement('div');
        const minterm = cellOrder[i];
        
        cell.className = 'kmap-cell';
        cell.dataset.minterm = minterm;
        
        // Add cell number
        const numberSpan = document.createElement('span');
        numberSpan.className = 'kmap-cell-number';
        numberSpan.textContent = minterm;
        
        // Determine cell value and styling
        if (minterms.includes(minterm)) {
            cell.classList.add('value-1');
            cell.innerHTML = '1';
        } else if (dontCares.includes(minterm)) {
            cell.classList.add('value-x');
            cell.innerHTML = 'X';
        } else {
            cell.classList.add('value-0');
            cell.innerHTML = '0';
        }
        
        // Add cell number after setting innerHTML
        cell.appendChild(numberSpan);
        
        // Add click handler for grouping
        cell.addEventListener('click', function() {
            toggleCellSelection(this);
        });
        
        grid.appendChild(cell);
    }
}

function create3VarKMap(grid, minterms, dontCares) {
    // Gray code ordering for 3-variable K-map (only first 8 cells)
    const cellOrder = [0, 1, 3, 2, 4, 5, 7, 6];
    
    for (let i = 0; i < 8; i++) {
        const cell = document.createElement('div');
        const minterm = cellOrder[i];
        
        cell.className = 'kmap-cell';
        cell.dataset.minterm = minterm;
        
        // Add cell number
        const numberSpan = document.createElement('span');
        numberSpan.className = 'kmap-cell-number';
        numberSpan.textContent = minterm;
        
        // Determine cell value and styling
        if (minterms.includes(minterm)) {
            cell.classList.add('value-1');
            cell.innerHTML = '1';
        } else if (dontCares.includes(minterm)) {
            cell.classList.add('value-x');
            cell.innerHTML = 'X';
        } else {
            cell.classList.add('value-0');
            cell.innerHTML = '0';
        }
        
        // Add cell number after setting innerHTML
        cell.appendChild(numberSpan);
        
        // Add click handler for grouping
        cell.addEventListener('click', function() {
            toggleCellSelection(this);
        });
        
        grid.appendChild(cell);
    }
}

function toggleCellSelection(cell) {
    const minterm = parseInt(cell.dataset.minterm);
    
    if (selectedCells.includes(minterm)) {
        // Remove from selection
        selectedCells = selectedCells.filter(m => m !== minterm);
        cell.classList.remove('grouped');
    } else {
        // Add to selection (only if it's a 1 or don't care)
        if (cell.classList.contains('value-1') || cell.classList.contains('value-x')) {
            selectedCells.push(minterm);
            cell.classList.add('grouped');
        }
    }
    
    updateResults();
}

function clearGroupings() {
    selectedCells = [];
    document.querySelectorAll('.kmap-cell').forEach(cell => {
        cell.classList.remove('grouped');
    });
    updateResults();
}

function updateResults() {
    const container = document.getElementById('resultContainer');
    if (!container) return;
    
    if (selectedCells.length === 0) {
        container.innerHTML = '';
        return;
    }
    
    const expression = generateBooleanExpression();
    
    container.innerHTML = `
        <div class="card result-card">
            <div class="card__body">
                <h3>Grouped Terms</h3>
                <p><strong>Selected minterms:</strong> ${selectedCells.sort((a, b) => a - b).join(', ')}</p>
                <div class="simplified-expression">
                    Simplified Expression: ${expression}
                </div>
                <p class="text-center"><em>Click on K-map cells (1s or Xs) to create groupings</em></p>
            </div>
        </div>
    `;
}

function generateBooleanExpression() {
    if (selectedCells.length === 0) return '';
    
    // Simple expression generation based on selected cells
    const variables = currentKMap.variables === 4 ? ['A', 'B', 'C', 'D'] : ['A', 'B', 'C'];
    
    if (selectedCells.length === 1) {
        return generateMintermExpression(selectedCells[0], variables);
    }
    
    // For multiple cells, try to find common patterns
    const expressions = selectedCells.map(minterm => generateMintermExpression(minterm, variables));
    
    // Simple grouping detection
    if (selectedCells.length === 2) {
        const diff = findBitDifference(selectedCells[0], selectedCells[1]);
        if (diff.count === 1) {
            return generateGroupExpression(selectedCells, variables, diff.position);
        }
    }
    
    return expressions.join(' + ');
}

function generateMintermExpression(minterm, variables) {
    const binary = minterm.toString(2).padStart(variables.length, '0');
    const terms = [];
    
    for (let i = 0; i < variables.length; i++) {
        if (binary[i] === '1') {
            terms.push(variables[i]);
        } else {
            terms.push(variables[i] + "'");
        }
    }
    
    return terms.join('');
}

function findBitDifference(a, b) {
    const xor = a ^ b;
    const count = xor.toString(2).split('1').length - 1;
    const position = Math.log2(xor);
    return { count, position };
}

function generateGroupExpression(minterms, variables, eliminatedBit) {
    const representative = minterms[0];
    const binary = representative.toString(2).padStart(variables.length, '0');
    const terms = [];
    
    for (let i = 0; i < variables.length; i++) {
        if (i === variables.length - 1 - eliminatedBit) {
            continue; // Skip the eliminated variable
        }
        
        if (binary[i] === '1') {
            terms.push(variables[i]);
        } else {
            terms.push(variables[i] + "'");
        }
    }
    
    return terms.join('');
}

// Quiz functionality
function initializeQuiz() {
    // Delay quiz initialization to ensure DOM is ready
    setTimeout(() => {
        renderQuiz();
    }, 100);
}

function renderQuiz() {
    const container = document.getElementById('quizContainer');
    if (!container) return;
    
    container.innerHTML = quizQuestions.map((question, index) => `
        <div class="question-card" data-question="${index}">
            <div class="question-header">
                <h3 class="question-title">Question ${index + 1}</h3>
            </div>
            <div class="question-body">
                <p class="question-text">${question.question}</p>
                <ul class="options-list">
                    ${question.options.map((option, optionIndex) => `
                        <li class="option-item">
                            <button class="option-button" data-question="${index}" data-option="${optionIndex}">
                                ${String.fromCharCode(65 + optionIndex)}. ${option}
                            </button>
                        </li>
                    `).join('')}
                </ul>
            </div>
        </div>
    `).join('');
    
    container.innerHTML += `
        <div class="quiz-actions">
            <button id="submitQuiz" class="btn btn--primary btn--lg">Submit Quiz</button>
            <button id="resetQuiz" class="btn btn--secondary btn--lg">Reset Quiz</button>
        </div>
    `;
    
    // Add event listeners
    const optionButtons = document.querySelectorAll('.option-button');
    optionButtons.forEach(button => {
        button.addEventListener('click', selectQuizOption);
    });
    
    const submitBtn = document.getElementById('submitQuiz');
    const resetBtn = document.getElementById('resetQuiz');
    
    if (submitBtn) {
        submitBtn.addEventListener('click', submitQuiz);
    }
    
    if (resetBtn) {
        resetBtn.addEventListener('click', resetQuiz);
    }
    
    // Initialize answers array
    quizAnswers = new Array(quizQuestions.length).fill(-1);
}

function selectQuizOption(event) {
    const button = event.target;
    const questionIndex = parseInt(button.dataset.question);
    const optionIndex = parseInt(button.dataset.option);
    
    // Remove selection from other options in this question
    document.querySelectorAll(`[data-question="${questionIndex}"]`).forEach(btn => {
        if (btn.classList.contains('option-button')) {
            btn.classList.remove('selected');
        }
    });
    
    // Select this option
    button.classList.add('selected');
    quizAnswers[questionIndex] = optionIndex;
}

function submitQuiz() {
    let correctCount = 0;
    
    quizQuestions.forEach((question, index) => {
        const userAnswer = quizAnswers[index];
        const correctAnswer = question.correct;
        const isCorrect = userAnswer === correctAnswer;
        
        if (isCorrect) correctCount++;
        
        // Update button styles
        document.querySelectorAll(`[data-question="${index}"]`).forEach(btn => {
            if (btn.classList.contains('option-button')) {
                const optionIndex = parseInt(btn.dataset.option);
                
                if (optionIndex === correctAnswer) {
                    btn.classList.add('correct');
                } else if (optionIndex === userAnswer && !isCorrect) {
                    btn.classList.add('incorrect');
                }
                
                btn.disabled = true;
            }
        });
    });
    
    displayQuizResults(correctCount);
}

function displayQuizResults(correctCount) {
    const container = document.getElementById('quizResults');
    if (!container) return;
    
    const percentage = Math.round((correctCount / quizQuestions.length) * 100);
    
    let scoreClass = 'score-needs-improvement';
    let message = 'Keep studying! Review the tutorial section.';
    
    if (percentage >= 80) {
        scoreClass = 'score-excellent';
        message = 'Excellent work! You have a solid understanding of don\'t care conditions.';
    } else if (percentage >= 60) {
        scoreClass = 'score-good';
        message = 'Good job! You understand the basics. Review the examples for better understanding.';
    }
    
    container.innerHTML = `
        <div class="card">
            <div class="card__body text-center">
                <h3>Quiz Results</h3>
                <div class="score-display ${scoreClass}">
                    ${correctCount}/${quizQuestions.length} (${percentage}%)
                </div>
                <p>${message}</p>
            </div>
        </div>
    `;
    
    container.scrollIntoView({ behavior: 'smooth' });
}

function resetQuiz() {
    quizAnswers = new Array(quizQuestions.length).fill(-1);
    
    // Reset all option buttons
    document.querySelectorAll('.option-button').forEach(button => {
        button.classList.remove('selected', 'correct', 'incorrect');
        button.disabled = false;
    });
    
    // Clear results
    const resultsContainer = document.getElementById('quizResults');
    if (resultsContainer) {
        resultsContainer.innerHTML = '';
    }
}