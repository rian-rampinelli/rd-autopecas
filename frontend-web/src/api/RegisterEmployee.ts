export default async function RegisterEmployee(employee: object) {
  const token = localStorage.getItem("token")
  const response = await fetch("http://localhost:8080/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
    },
    body: JSON.stringify(employee),
  })

  if (!response.ok) {
    throw new Error("Erro ao cadastrar funcionário")
  }

}

