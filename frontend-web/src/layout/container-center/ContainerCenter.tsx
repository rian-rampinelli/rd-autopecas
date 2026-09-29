import type { ReactNode } from "react"

function ContainerCenter({children,className}: {children:ReactNode,className?: string}) {
    return (
        <div className={`flex justify-center items-center bg-[#111111] min-h-[100vh]  ${className ?? ""}`}>
            {children}
        </div>
    )
}

export default ContainerCenter