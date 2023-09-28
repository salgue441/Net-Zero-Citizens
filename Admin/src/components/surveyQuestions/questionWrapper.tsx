import Switch from '@mui/material/Switch'
import { useState } from 'react'
import RemoveCircleOutlineIcon from '@mui/icons-material/RemoveCircleOutline'
import { MulOPQuestion } from './questionsTypes/mulOpQuestion'
import { Question } from '../../app/encuestas/crear/page'

type QuestionWrapperProps = {
  questions: Question[]
  id: number
  setQuestions: React.Dispatch<React.SetStateAction<Question[]>>
}
export const QuestionWrapper = ({
  questions,
  id,
  setQuestions,
}: QuestionWrapperProps) => {
  const [required, setRequired] = useState(false)
  const [questionType, setQuestionType] = useState('openQuestion')

  const handleQuestionTypeChange = (
    event: React.ChangeEvent<HTMLSelectElement>
  ) => {
    setQuestionType(event.target.value)
  }

  const handleSwitchChange = () => {
    setRequired(!required)
  }

  const questionSwitch = () => {
    if (questionType === 'mulOptionQuestion') {
      return <MulOPQuestion />
    } else {
      return
    }
  }

  const deleteQuestion = () => {
    const newQuestions = questions.filter((question) => question.id !== id)
    setQuestions(newQuestions)
  }

  return (
    <div className="rounded border-2 border-solid border-gray-300 p-3.5 h-fit flex flex-col mb-3">
      <div className="flex flex-row justify-between">
        <h2 className="font-bold">
          Pregunta {questions.map((q) => q.id).indexOf(id) + 1}
        </h2>
        <a onClick={deleteQuestion}>
          <RemoveCircleOutlineIcon className="hover:text-red-600 cursor-pointer" />
        </a>
      </div>

      <div className="flex flex-row justify-between mt-3">
        <input
          id="title"
          name="title"
          type="text"
          className="px-2 py-2 mb-3 rounded border border-solid border-gray-300 w-3/4 h-11"
          placeholder="Cual es tu huella de carbono?"
        />
        <select
          className="rounded border border-solid border-gray-300 px-2 py-2 h-11"
          onChange={handleQuestionTypeChange}
        >
          <option value="openQuestion">Pregunta Abierta</option>
          <option value="mulOptionQuestion">Opcion Multiple</option>
          <option value="scalenQuestion">Pregunta Escalar</option>
        </select>
      </div>
      <div className="flex flex-col w-full">{questionSwitch()}</div>

      <div className="flex flex-row">
        <label
          className="flex items-center space-x-2"
          style={{ pointerEvents: 'none' }}
        >
          <span>Obligatoria</span>
        </label>
        <div style={{ pointerEvents: 'auto' }}>
          <Switch
            checked={required}
            onChange={handleSwitchChange}
            color="primary"
            inputProps={{ 'aria-label': 'toggle switch' }}
          />
        </div>
      </div>
    </div>
  )
}
