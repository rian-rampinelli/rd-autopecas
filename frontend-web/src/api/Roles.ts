export default async function GetRoles() {
  const token = localStorage.getItem("token")
  const response = await fetch("http://localhost:8080/roles", {
    headers: { Authorization: `Bearer ${token}` },
  })
  if (!response.ok){
    throw new Error("Erro ao buscar funções")

  } 
  return await response.json()
}
