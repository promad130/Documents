// Application state
let currentKMap = null;
let selectedCells = [];
let quizAnswers = [];

// Quiz questions data
const quizQuestions = [
    {
        question: "In POS form, what do we group in the K-map?",
        options: ["The 1s (minterms)", "The 0s (maxterms)", "Both 1s and 0s", "Neither"],
        correct: 1
    },
    {
        question: "When is POS form typically simpler than SOP?",
        options: ["When there are more 1s than 0s", "When there are more 0s than 1s", "When variables are all the same", "Never"],
        correct: 0
    },
    {
        question: "What does π(0,2,5) represent?",
        options: ["Sum of minterms 0,2,5", "Product of maxterms M0, M2, M5", "Variables 0,2,5", "Nothing meaningful"],
        correct: 1
    }
];

// Practice problems with solutions
const practiceProblems = [
    {
        function: "F(A,B,C) = π(0,3,6,7)",
        solution: "(B+C)(A'+C')",
        hint: "Look for pairs and individual terms"
    },
    {
        function: "F(W,X,Y,Z) = π(1,4,5,10,11,14)",
        solution: "(W+X')(X+Z')",
        hint: "Try to form quads first"
    },
    {
        function: "F(A,B,C,D) = π(2,3,8,9,12,13)",
        solution: "(A'+C')(B+D')",
        hint: "Look for two groups of 3 terms each"
    }
];

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    initializeNavigation();
    initializeKMap();
    initializePractice();
    initializeQuiz();
    initializeComparison();
    
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
            showSection(sectionName);
            
            // Update active button
            navButtons.forEach(btn => btn.classList.remove('active'));
            this.classList.add('active');
        });
    });
}

function showSection(sectionName) {
    // Hide all sections
    const allSections = document.querySelectorAll('.section');
    allSections.forEach(section => {
        section.classList.remove('active');
    });
    
    // Show selected section
    const targetSection = document.getElementById(sectionName);
    if (targetSection) {
        targetSection.classList.add('active');
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
    
    // Generate initial K-map
    setTimeout(() => {
        generateKMap();
    }, 100);
}

function generateKMap() {
    const variableCountEl = document.getElementById('variableCount');
    const maxtermsEl = document.getElementById('maxterms');
    
    if (!variableCountEl || !maxtermsEl) {
        return;
    }
    
    const variableCount = parseInt(variableCountEl.value);
    const maxterms = parseNumbers(maxtermsEl.value);
    
    currentKMap = {
        variables: variableCount,
        maxterms: maxterms
    };
    
    createKMapGrid(variableCount, maxterms);
    createKMapLabels(variableCount);
    selectedCells = [];
    updateResults();
}

function parseNumbers(input) {
    if (!input || !input.trim()) return [];
    return input.split(',').map(n => parseInt(n.trim())).filter(n => !isNaN(n));
}

function createKMapLabels(variables) {
    const topLabels = document.getElementById('topLabels');
    const leftLabels = document.getElementById('leftLabels');
    
    if (!topLabels || !leftLabels) return;
    
    if (variables === 4) {
        topLabels.innerHTML = `
            <div style="font-weight: bold; margin-bottom: 8px;">CD</div>
            <div style="display: flex; gap: 12px;">
                <span>00</span><span>01</span><span>11</span><span>10</span>
            </div>
        `;
        leftLabels.innerHTML = `
            <div style="font-weight: bold; margin-right: 12px; writing-mode: vertical-lr;">AB</div>
            <div style="display: flex; flex-direction: column; gap: 8px; justify-content: space-around; height: 240px;">
                <span>00</span><span>01</span><span>11</span><span>10</span>
            </div>
        `;
    } else {
        topLabels.innerHTML = `
            <div style="font-weight: bold; margin-bottom: 8px;">BC</div>
            <div style="display: flex; gap: 12px;">
                <span>00</span><span>01</span><span>11</span><span>10</span>
            </div>
        `;
        leftLabels.innerHTML = `
            <div style="font-weight: bold; margin-right: 12px; writing-mode: vertical-lr;">A</div>
            <div style="display: flex; flex-direction: column; gap: 8px; justify-content: space-around; height: 120px;">
                <span>0</span><span>1</span>
            </div>
        `;
    }
}

function createKMapGrid(variables, maxterms) {
    const grid = document.getElementById('kmapGrid');
    if (!grid) return;
    
    grid.innerHTML = '';
    
    if (variables === 3) {
        grid.className = 'kmap-grid grid-3var';
        create3VarKMapPOS(grid, maxterms);
    } else {
        grid.className = 'kmap-grid grid-4var';
        create4VarKMapPOS(grid, maxterms);
    }
}

function create4VarKMapPOS(grid, maxterms) {
    // Gray code ordering for 4-variable K-map
    const cellOrder = [
        0, 1, 3, 2,    // AB=00
        4, 5, 7, 6,    // AB=01
        12, 13, 15, 14, // AB=11
        8, 9, 11, 10   // AB=10
    ];
    
    for (let i = 0; i < 16; i++) {
        const cell = document.createElement('div');
        const cellValue = cellOrder[i];
        
        cell.className = 'kmap-cell';
        cell.dataset.cellValue = cellValue;
        
        // Add cell number
        const numberSpan = document.createElement('span');
        numberSpan.className = 'kmap-cell-number';
        numberSpan.textContent = cellValue;
        
        // For POS, maxterms are 0s, everything else is 1
        if (maxterms.includes(cellValue)) {
            cell.classList.add('value-0');
            cell.innerHTML = '0';
        } else {
            cell.classList.add('value-1');
            cell.innerHTML = '1';
        }
        
        cell.appendChild(numberSpan);
        
        // Add click handler for grouping (only for 0s)
        cell.addEventListener('click', function() {
            if (this.classList.contains('value-0')) {
                toggleCellSelection(this);
            }
        });
        
        grid.appendChild(cell);
    }
}

function create3VarKMapPOS(grid, maxterms) {
    // Gray code ordering for 3-variable K-map
    const cellOrder = [0, 1, 3, 2, 4, 5, 7, 6];
    
    for (let i = 0; i < 8; i++) {
        const cell = document.createElement('div');
        const cellValue = cellOrder[i];
        
        cell.className = 'kmap-cell';
        cell.dataset.cellValue = cellValue;
        
        // Add cell number
        const numberSpan = document.createElement('span');
        numberSpan.className = 'kmap-cell-number';
        numberSpan.textContent = cellValue;
        
        // For POS, maxterms are 0s, everything else is 1
        if (maxterms.includes(cellValue)) {
            cell.classList.add('value-0');
            cell.innerHTML = '0';
        } else {
            cell.classList.add('value-1');
            cell.innerHTML = '1';
        }
        
        cell.appendChild(numberSpan);
        
        // Add click handler for grouping (only for 0s)
        cell.addEventListener('click', function() {
            if (this.classList.contains('value-0')) {
                toggleCellSelection(this);
            }
        });
        
        grid.appendChild(cell);
    }
}

function toggleCellSelection(cell) {
    const cellValue = parseInt(cell.dataset.cellValue);
    
    if (selectedCells.includes(cellValue)) {
        // Remove from selection
        selectedCells = selectedCells.filter(c => c !== cellValue);
        cell.classList.remove('grouped');
    } else {
        // Add to selection
        selectedCells.push(cellValue);
        cell.classList.add('grouped');
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
    
    const groups = analyzeGroups(selectedCells);
    const posExpression = generatePOSExpression(groups);
    
    container.innerHTML = `
        <div class="card result-card">
            <div class="card__body">
                <h3>POS Analysis</h3>
                <p><strong>Selected maxterms (0s):</strong> ${selectedCells.sort((a, b) => a - b).join(', ')}</p>
                <p><strong>Groups formed:</strong> ${groups.length}</p>
                <div class="pos-result">
                    POS Expression: ${posExpression}
                </div>
                <p class="text-center"><em>Click on cells with 0s to create groupings for POS simplification</em></p>
            </div>
        </div>
    `;
}

function analyzeGroups(selectedCells) {
    // Simple group analysis - in a real implementation, this would be more sophisticated
    const groups = [];
    const remaining = [...selectedCells];
    
    // Check for pairs and singles
    while (remaining.length > 0) {
        const current = remaining[0];
        const group = [current];
        remaining.splice(0, 1);
        
        // Look for adjacent cells
        for (let i = remaining.length - 1; i >= 0; i--) {
            if (areAdjacent(current, remaining[i], currentKMap.variables)) {
                group.push(remaining[i]);
                remaining.splice(i, 1);
                break; // Simple pairing for demo
            }
        }
        
        groups.push(group);
    }
    
    return groups;
}

function areAdjacent(cell1, cell2, variables) {
    // Check if two cells are adjacent in the K-map
    const diff = cell1 ^ cell2;
    const hammingDistance = diff.toString(2).split('1').length - 1;
    
    if (hammingDistance === 1) return true;
    
    // Check for wrap-around adjacency
    if (variables === 4) {
        const pos1 = getKMapPosition4Var(cell1);
        const pos2 = getKMapPosition4Var(cell2);
        
        return (Math.abs(pos1.row - pos2.row) <= 1 && Math.abs(pos1.col - pos2.col) <= 1) ||
               (pos1.row === pos2.row && (Math.abs(pos1.col - pos2.col) === 3)) ||
               (pos1.col === pos2.col && (Math.abs(pos1.row - pos2.row) === 3));
    }
    
    return false;
}

function getKMapPosition4Var(cellValue) {
    const cellOrder = [
        0, 1, 3, 2,    // Row 0
        4, 5, 7, 6,    // Row 1  
        12, 13, 15, 14, // Row 2
        8, 9, 11, 10   // Row 3
    ];
    
    const index = cellOrder.indexOf(cellValue);
    return {
        row: Math.floor(index / 4),
        col: index % 4
    };
}

function generatePOSExpression(groups) {
    if (groups.length === 0) return '';
    
    const variables = currentKMap.variables === 4 ? ['A', 'B', 'C', 'D'] : ['A', 'B', 'C'];
    const sumTerms = groups.map(group => generateSumTerm(group, variables));
    
    return sumTerms.join('');
}

function generateSumTerm(group, variables) {
    if (group.length === 1) {
        // Single maxterm
        return generateMaxterm(group[0], variables);
    }
    
    // For grouped terms, find common variables
    const representative = group[0];
    const binary = representative.toString(2).padStart(variables.length, '0');
    const terms = [];
    
    // Simple term generation for demo
    for (let i = 0; i < variables.length; i++) {
        if (binary[i] === '1') {
            terms.push(variables[i] + "'");
        } else {
            terms.push(variables[i]);
        }
    }
    
    return '(' + terms.join('+') + ')';
}

function generateMaxterm(cellValue, variables) {
    const binary = cellValue.toString(2).padStart(variables.length, '0');
    const terms = [];
    
    for (let i = 0; i < variables.length; i++) {
        if (binary[i] === '1') {
            terms.push(variables[i] + "'");
        } else {
            terms.push(variables[i]);
        }
    }
    
    return '(' + terms.join('+') + ')';
}

// Comparison tool
function initializeComparison() {
    const compareBtn = document.getElementById('compareFunction');
    if (compareBtn) {
        compareBtn.addEventListener('click', compareSOPvsPOS);
    }
}

function compareSOPvsPOS() {
    const mintermsEl = document.getElementById('comparisonMinterms');
    if (!mintermsEl) return;
    
    const minterms = parseNumbers(mintermsEl.value);
    if (minterms.length === 0) return;
    
    // Calculate maxterms (complement of minterms for 3-variable)
    const allTerms = Array.from({length: 8}, (_, i) => i);
    const maxterms = allTerms.filter(term => !minterms.includes(term));
    
    const sopResult = generateSOPFromMinterms(minterms);
    const posResult = generatePOSFromMaxterms(maxterms);
    
    const resultsContainer = document.getElementById('comparisonResults');
    if (resultsContainer) {
        resultsContainer.innerHTML = `
            <div class="card">
                <div class="card__body">
                    <h3>Function: F(A,B,C) = Σ(${minterms.join(',')}) = π(${maxterms.join(',')})</h3>
                    
                    <div class="comparison-grid">
                        <div class="comparison-item">
                            <h4>SOP Form</h4>
                            <div class="pos-result">${sopResult.expression}</div>
                            <p><strong>Literal count:</strong> ${sopResult.literals}</p>
                        </div>
                        
                        <div class="comparison-item">
                            <h4>POS Form</h4>
                            <div class="pos-result">${posResult.expression}</div>
                            <p><strong>Literal count:</strong> ${posResult.literals}</p>
                        </div>
                    </div>
                    
                    <div class="savings ${posResult.literals < sopResult.literals ? 'correct' : 'incorrect'}">
                        ${posResult.literals < sopResult.literals ? 'POS form is simpler!' : 'SOP form is simpler!'}
                        ${posResult.literals === sopResult.literals ? 'Both forms have equal complexity!' : ''}
                    </div>
                </div>
            </div>
        `;
        
        // Show the existing comparison image
        const comparisonVisual = resultsContainer.querySelector('.comparison-visual');
        if (comparisonVisual) {
            comparisonVisual.style.display = 'block';
        }
    }
}

function generateSOPFromMinterms(minterms) {
    const variables = ['A', 'B', 'C'];
    const terms = minterms.map(minterm => {
        const binary = minterm.toString(2).padStart(3, '0');
        const termVars = binary.split('').map((bit, i) => 
            bit === '1' ? variables[i] : variables[i] + "'"
        );
        return termVars.join('');
    });
    
    return {
        expression: terms.join(' + '),
        literals: terms.reduce((sum, term) => sum + term.replace(/'/g, '').length, 0)
    };
}

function generatePOSFromMaxterms(maxterms) {
    const variables = ['A', 'B', 'C'];
    const terms = maxterms.map(maxterm => {
        const binary = maxterm.toString(2).padStart(3, '0');
        const termVars = binary.split('').map((bit, i) => 
            bit === '1' ? variables[i] + "'" : variables[i]
        );
        return '(' + termVars.join('+') + ')';
    });
    
    return {
        expression: terms.join(''),
        literals: maxterms.length * 3 // Each maxterm has 3 literals in 3-variable function
    };
}

// Practice functionality
function initializePractice() {
    const checkButtons = document.querySelectorAll('.check-answer');
    checkButtons.forEach((button, index) => {
        button.addEventListener('click', () => checkPracticeAnswer(index));
    });
}

function checkPracticeAnswer(problemIndex) {
    const problemDiv = document.querySelector(`[data-problem="${problemIndex}"]`);
    const answerInput = problemDiv.querySelector('.practice-answer');
    const feedbackDiv = problemDiv.querySelector('.practice-feedback');
    
    const userAnswer = answerInput.value.trim();
    const correctAnswer = practiceProblems[problemIndex].solution;
    
    // Simple answer checking (normalize spaces and case)
    const normalizedUser = userAnswer.replace(/\s+/g, '').toLowerCase();
    const normalizedCorrect = correctAnswer.replace(/\s+/g, '').toLowerCase();
    
    if (normalizedUser === normalizedCorrect || checkEquivalentPOS(normalizedUser, normalizedCorrect)) {
        feedbackDiv.className = 'practice-feedback correct';
        feedbackDiv.innerHTML = `
            <strong>Correct!</strong> Well done! Your answer ${userAnswer} is correct.
        `;
    } else {
        feedbackDiv.className = 'practice-feedback incorrect';
        feedbackDiv.innerHTML = `
            <strong>Incorrect.</strong> The correct answer is: <code>${correctAnswer}</code><br>
            <em>Hint: ${practiceProblems[problemIndex].hint}</em>
        `;
    }
}

function checkEquivalentPOS(answer1, answer2) {
    // Basic equivalence check - in practice, this would be more sophisticated
    return answer1 === answer2;
}

// Quiz functionality
function initializeQuiz() {
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
    let message = 'Keep studying! Review the tutorial section and practice more.';
    
    if (percentage >= 80) {
        scoreClass = 'score-excellent';
        message = 'Excellent work! You have mastered POS form in K-maps.';
    } else if (percentage >= 60) {
        scoreClass = 'score-good';
        message = 'Good job! You understand the basics. Practice the examples for better mastery.';
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