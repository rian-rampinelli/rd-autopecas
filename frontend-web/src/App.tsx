import { Routes,Route } from "react-router-dom"
import Login from "./pages/login/Login"
import RegisterEmployee from "./pages/register-employee/RegisterEmployee"

function App() {
  return (
    <>
    <Routes>
      <Route path="/" element={<Login></Login>}></Route>
      <Route path="/login" element={<Login></Login>}></Route>
      <Route path="/register" element={<RegisterEmployee></RegisterEmployee>}></Route>
    </Routes>
    </>
  )
}

export default App
