export default async function FazerLogin(email:string, password:string) {
    try {
        const response = await fetch("http://localhost:8080/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
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