import { Routes,Route } from "react-router-dom"
import Login from "./pages/login/Login"
import Register from "./pages/register/Register"

function App() {
  return (
    <>
    <Routes>
      <Route path="/" element={<Login></Login>}></Route>
      <Route path="/login" element={<Login></Login>}></Route>
      <Route path="/register" element={<Register></Register>}></Route>
    </Routes>
    </>
  )
}

export default App
