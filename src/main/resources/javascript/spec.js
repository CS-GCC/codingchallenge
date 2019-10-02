const Promise = require('bluebird')
const performance = require('performance-now')
const question01 = require('./questions/question1')
const question02 = require('./questions/question2')
const question03 = require('./questions/question3')
const question04 = require('./questions/question4')
const question05 = require('./questions/question5')
const question06 = require('./questions/question6')

import { describe, it } from 'jest'

describe('Coding Challenge Skeleton Tests', () => {

    describe('Q1', () => {

        it('runs question 1', () => {
            const tests = getTests(1)
            let promises = []
            for (let i = 0; i < tests.length; i++) {
                try {
                    const testRunner = new Promise((resolve, reject) => {
                        const test = tests[i]
                        const runtest = runq1
                        runtest(test)
                            .timeout(1000)
                            .then(({ answer, timeTaken }) => {
                                correct = answer == test["output"]
                                resolve({
                                    "questionNumber": 1,
                                    "testNumber": i,
                                    "correct": correct,
                                    "speed": timeTaken * 1000000
                                })
                            })
                            .catch(Promise.TimeoutError, (e) => {
                                console.log("A question 1 test has timed out. Each individual test has a maximum of one second to run.")
                                resolve({
                                    "questionNumber": 1,
                                    "testNumber": i,
                                    "correct": "TIMED_OUT",
                                    "speed": -1
                                })
                            })
                    })
                    promises.push(testRunner)
                } catch (error) {
                    console.log(error)
                }
            }
            Promise.all(promises).then((response) => submitAnswers(response, 1))

        })

    })

    describe('Q2', () => {

        it('runs question 2', () => {
            const tests = getTests(2)
            let promises = []
            for (let i = 0; i < tests.length; i++) {
                try {
                    const testRunner = new Promise((resolve, reject) => {
                        const test = tests[i]
                        const runtest = runq2
                        runtest(test)
                            .timeout(1000)
                            .then(({ answer, timeTaken }) => {
                                correct = answer == test["output"]
                                resolve({
                                    "questionNumber": 2,
                                    "testNumber": i,
                                    "correct": correct,
                                    "speed": timeTaken * 1000000
                                })
                            })
                            .catch(Promise.TimeoutError, (e) => {
                                console.log("A question 2 test has timed out. Each individual test has a maximum of one second to run.")
                                resolve({
                                    "questionNumber": 2,
                                    "testNumber": i,
                                    "correct": "TIMED_OUT",
                                    "speed": -1
                                })
                            })
                    })
                    promises.push(testRunner)
                } catch (error) {
                    console.log(error)
                }
            }
            Promise.all(promises).then((response) => submitAnswers(response, 2))

        })

    })

    describe('Q3', () => {

        it('runs question 3', () => {
            const tests = getTests(3)
            let promises = []
            for (let i = 0; i < tests.length; i++) {
                try {
                    const testRunner = new Promise((resolve, reject) => {
                        const test = tests[i]
                        const runtest = runq3
                        runtest(test)
                            .timeout(1000)
                            .then(({ answer, timeTaken }) => {
                                correct = answer == test["output"]
                                resolve({
                                    "questionNumber": 3,
                                    "testNumber": i,
                                    "correct": correct,
                                    "speed": timeTaken * 1000000
                                })
                            })
                            .catch(Promise.TimeoutError, (e) => {
                                console.log("A question 3 test has timed out. Each individual test has a maximum of one second to run.")
                                resolve({
                                    "questionNumber": 3,
                                    "testNumber": i,
                                    "correct": "TIMED_OUT",
                                    "speed": -1
                                })
                            })
                    })
                    promises.push(testRunner)
                } catch (error) {
                    console.log(error)
                }
            }
            Promise.all(promises).then((response) => submitAnswers(response, 3))

        })

    })

    describe('Q4', () => {

        it('runs question 4', () => {
            const tests = getTests(4)
            let promises = []
            for (let i = 0; i < tests.length; i++) {
                try {
                    const testRunner = new Promise((resolve, reject) => {
                        const test = tests[i]
                        const runtest = runq4
                        runtest(test)
                            .timeout(1000)
                            .then(({ answer, timeTaken }) => {
                                correct = answer == test["output"]
                                resolve({
                                    "questionNumber": 4,
                                    "testNumber": i,
                                    "correct": correct,
                                    "speed": timeTaken * 1000000
                                })
                            })
                            .catch(Promise.TimeoutError, (e) => {
                                console.log("A question 4 test has timed out. Each individual test has a maximum of one second to run.")
                                resolve({
                                    "questionNumber": 4,
                                    "testNumber": i,
                                    "correct": "TIMED_OUT",
                                    "speed": -1
                                })
                            })
                    })
                    promises.push(testRunner)
                } catch (error) {
                    console.log(error)
                }
            }
            Promise.all(promises).then((response) => submitAnswers(response, 4))

        })

    })

    describe('Q5', () => {

        it('runs question 5', () => {
            const tests = getTests(5)
            let promises = []
            for (let i = 0; i < tests.length; i++) {
                try {
                    const testRunner = new Promise((resolve, reject) => {
                        const test = tests[i]
                        const runtest = runq5
                        runtest(test)
                            .timeout(1000)
                            .then(({ answer, timeTaken }) => {
                                correct = answer == test["output"]
                                resolve({
                                    "questionNumber": 5,
                                    "testNumber": i,
                                    "correct": correct,
                                    "speed": timeTaken * 1000000
                                })
                            })
                            .catch(Promise.TimeoutError, (e) => {
                                console.log("A question 5 test has timed out. Each individual test has a maximum of one second to run.")
                                resolve({
                                    "questionNumber": 5,
                                    "testNumber": i,
                                    "correct": "TIMED_OUT",
                                    "speed": -1
                                })
                            })
                    })
                    promises.push(testRunner)
                } catch (error) {
                    console.log(error)
                }
            }
            Promise.all(promises).then((response) => submitAnswers(response, 5))

        })

    })

    describe('Q6', () => {

        it('runs question 6', () => {
            const tests = getTests(6)
            let promises = []
            for (let i = 0; i < tests.length; i++) {
                try {
                    const testRunner = new Promise((resolve, reject) => {
                        const test = tests[i]
                        const runtest = runq6
                        runtest(test)
                            .timeout(1000)
                            .then(({ answer, timeTaken }) => {
                                correct = answer == test["output"]
                                resolve({
                                    "questionNumber": 6,
                                    "testNumber": i,
                                    "correct": correct,
                                    "speed": timeTaken * 1000000
                                })
                            })
                            .catch(Promise.TimeoutError, (e) => {
                                console.log("A question 6 test has timed out. Each individual test has a maximum of one second to run.")
                                resolve({
                                    "questionNumber": 6,
                                    "testNumber": i,
                                    "correct": "TIMED_OUT",
                                    "speed": -1
                                })
                            })
                    })
                    promises.push(testRunner)
                } catch (error) {
                    console.log(error)
                }
            }
            Promise.all(promises).then((response) => submitAnswers(response, 6))

        })

    })

})

const getTests = (question) => {
    const uuid = process.env.travis_uuid
    let url = `https://cscc-gl.herokuapp.com/tests/run/${question}`
    if (uuid != undefined) {
        url = `${url}/${uuid}`
    }
    const response = await fetch(url)
    const tests = await response.json()
    return tests
}

const submitAnswers = (answers, question) => {
    const uuid = process.env.travis_uuid
    if (uuid != undefined) {
        const url = `https://cscc-gl.herokuapp.com/answer/contestant/${uuid}/${question}`
        await fetch(url, {
            method: 'POST',
            body: JSON.stringify(answers)
        })
    }
}

function runq1(test) {
    return new Promise((resolve, reject) => {
        const input = json.parse(test['input'])
        const t0 = performance()
        const answer = question01(input.initialLevelOfDebt, input.interestPercentage, input.repaymentPercentage)
        const t1 = performance()
        const timeTaken = t1 - t0
        return resolve({
            answer,
            timeTaken
        })
    })
}

function runq2(test) {
    return new Promise((resolve, reject) => {
        const input = json.parse(test['input'])
        const t0 = performance()
        const answer = question02(input.risk, input.bonus, input.trader)
        const t1 = performance()
        const timeTaken = t1 - t0
        return resolve({
            answer,
            timeTaken
        })
    })
}

function runq3(test) {
    return new Promise((resolve, reject) => {
        const input = json.parse(test['input'])
        const t0 = performance()
        const answer = question03(input.scores, input.alice)
        const t1 = performance()
        const timeTaken = t1 - t0
        return resolve({
            answer,
            timeTaken
        })
    })
}

function runq4(test) {
    return new Promise((resolve, reject) => {
        const input = json.parse(test['input'])
        const t0 = performance()
        const answer = question04(input.v, input.c, input.mc)
        const t1 = performance()
        const timeTaken = t1 - t0
        return resolve({
            answer,
            timeTaken
        })
    })
}

function runq5(test) {
    return new Promise((resolve, reject) => {
        const input = json.parse(test['input'])
        const t0 = performance()
        const answer = question05(input)
        const t1 = performance()
        const timeTaken = t1 - t0
        return resolve({
            answer,
            timeTaken
        })
    })
}

function runq6(test) {
    return new Promise((resolve, reject) => {
        const input = json.parse(test['input'])
        const t0 = performance()
        const answer = question06(input)
        const t1 = performance()
        const timeTaken = t1 - t0
        return resolve({
            answer,
            timeTaken
        })
    })
}