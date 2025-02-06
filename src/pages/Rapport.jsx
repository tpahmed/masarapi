"use client"
import {  ClipboardPlus } from 'lucide-react'
import { Button } from "@/components/ui/button"
import { columns } from "@/components/rapport/columns"
import { DataTable } from "@/components/rapport/data-table"
import { SidebarProvider, SidebarInset } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { AddRapportSheet } from "@/components/rapport/add-rapport-sheet"

const data = [
  {
    id: "Rapport-1234",
    generatedBy: "ahmed@school.ma",
    Date: "2023-07-10",
    Type: "Rapport formel",
    Content: "the problem with the school network",
  },
  {
    id: "Rapport-4567",
    generatedBy: "sara@school.ma",
    Date: "2023-07-15",
    Type: "Rapport formel",
    Content: "Student attendance tracking system issues",
  },
  {
    id: "Rapport-8782",
    generatedBy: "mohamed@school.ma",
    Date: "2023-07-18",
    Type: "Rapport formel",
    Content: "Laboratory equipment maintenance request",
  },
  {
    id: "Rapport-7878",
    generatedBy: "fatima@school.ma",
    Date: "2023-07-20",
    Type: "Rapport formel",
    Content: "Library resources update needed",
  },
  {
    id: "Rapport-5562",
    generatedBy: "karim@school.ma",
    Date: "2023-07-22",
    Type: "Rapport formel",
    Content: "Classroom projector malfunction in Room 204",
  },
  {
    id: "Rapport-8686",
    generatedBy: "yasmine@school.ma",
    Date: "2023-07-25",
    Type: "Rapport formel",
    Content: "Cafeteria food quality concerns",
  },
  {
    id: "Rapport-9123",
    generatedBy: "omar@school.ma",
    Date: "2023-07-28",
    Type: "Rapport formel",
    Content: "Sports equipment inventory check",
  }
]



export default function Tasks() {

  return (
    <SidebarProvider>
    <div className="flex min-h-screen dark:bg-background">
      <AppSidebar className="border-r border-gray-300 shadow-[2px_0_10px_rgb(107,114,128)]" />
      <SidebarInset className="flex-1 ">
        <div className="flex flex-col bg-gray-200 h-full">
          <Header />
      <div className=" p-4">
        <div className="flex items-center justify-between mb-6">
          <div>
            <h1 className="text-2xl font-semibold">Rapport</h1>
            <p className="text-muted-foreground">
            Heres a list of your Rapport !
            </p>
          </div>
          <div className="flex gap-2">
          <AddRapportSheet/>
          </div>
        </div>

        <DataTable columns={columns} data={data} />
      </div>

    </div>
        </SidebarInset>
            </div>
        </SidebarProvider>
  )
}

