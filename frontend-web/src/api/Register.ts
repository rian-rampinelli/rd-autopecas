export default async function Registrar(email:string, senha:string) {
    try {
        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                senha: senha
            })
        });

        if (!response.ok) {
            throw new Error("Erro ao realizar login");
        }

        const data = await response.json();

        return data;

    } catch (error) {
        console.error("Erro no login:", error);
        throw error;
    }
}