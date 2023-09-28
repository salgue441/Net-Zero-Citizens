'use client'
import { QuestionWrapper } from '../../../components/surveyQuestions/questionWrapper'
import AddIcon from '@mui/icons-material/Add'
import { MouseEventHandler, useState } from 'react'

export type CreateSurveyBody = {
  description: string
  title: string
  questions: Question[]
}

export type Question = {
  id: number
  title: string
  type: string
  required: boolean
  options?: string[]
}

export default function CreateSurvey() {
  const [counter, setCounter] = useState(1)
  const [questions, setQuestions] = useState<Question[]>([
    {
      id: 0,
      title: '',
      type: 'openQuestion',
      required: false,
    },
  ])

  const [survey, setSurvey] = useState<CreateSurveyBody>({
    description: '',
    title: '',
    questions: questions,
  })

  const createQuestion: MouseEventHandler<HTMLAnchorElement> = (e) => {
    e.preventDefault()
    const newQuestion = {
      id: counter,
      title: '',
      type: 'openQuestion',
      required: false,
    }
    setCounter(counter + 1)
    setQuestions((prevQuestions) => [...prevQuestions, newQuestion])
    setSurvey((prevSurvey) => {
      const newSurvey = { ...prevSurvey }
      newSurvey.questions = [...prevSurvey.questions, newQuestion]
      console.log(newSurvey)
      return newSurvey
    })
  }

  const handleSurveyTitleChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSurvey((prevSurvey) => {
      const newSurvey = { ...prevSurvey, title: event.target.value }
      return newSurvey
    })
  }

  const handleSurveyDescriptionChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSurvey((prevSurvey) => {
      const newSurvey = { ...prevSurvey, description: event.target.value }
      return newSurvey
    })
  }

  return (
    <div>
      <form>
        <div className="flex-row flex items-center justify-between my-8 mx-8">
          <h1 className="self-start font-extrabold my-8 mx-8 text-4xl text-txt">
            Crear Encuesta
          </h1>
          <a className="flex items-center justify-center px-4 py-2 my-8 mx-8 text-white bg-primary-base rounded hover:bg-primary-dark">
            Crear
          </a>
        </div>
        <div className="flex-row flex justify-evenly my-8 mx-8">
          <div className="flex flex-col mx-8">
            <label className="text-m font-bold text-txt mb-2">
              Titulo de la encuesta
            </label>
            <input
              id="title"
              name="title"
              type="text"
              className="px-4 py-2 mb-3 border border-gray-700 rounded"
              placeholder="Calcula tu huella de carbono"
              onChange={handleSurveyTitleChange}
            />
            <label className="text-m font-bold text-txt mb-2">
              Descripción
            </label>
            <input
              id="description"
              name="description"
              type="text"
              className="px-4 py-2 border border-gray-700 rounded h"
              placeholder="Encuesta para calcular tu huella de carbono"
              onChange={handleSurveyDescriptionChange}
            />
          </div>
          <div className="flex flex-col w-1/2">
            <div className="w-full">
              {questions.map((question) => (
                <QuestionWrapper
                  questions={questions}
                  id={question.id}
                  setQuestions={setQuestions}
                  key={question.id}
                  survey={survey}
                  setSurvey={setSurvey}
                />
              ))}
            </div>
            <div className="flex flex-row">
              <a
                className=" w-full flex items-center justify-center px-4 py-2 my-8 text-white bg-primary-base rounded cursor-pointer hover:bg-primary-dark"
                onClick={(e) => createQuestion(e)}
              >
                Anadir Pregunta
                <AddIcon />
              </a>
            </div>
          </div>
        </div>
      </form>
    </div>
  )
}
