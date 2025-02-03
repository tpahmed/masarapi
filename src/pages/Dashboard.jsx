import Sidebar from "@/layout/Sidebar"

export default function Dashboard() {
    return (
        <>
        <Sidebar/>
        <div className="flex flex-col gap-6">
            <h1 className="text-2xl font-bold">Dashboard</h1>
        </div>
        </>
    )
}
