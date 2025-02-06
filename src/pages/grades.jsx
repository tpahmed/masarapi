"use client"
import { useState } from "react"
import { DataTable } from "@/components/grades/data-table"
import { SidebarProvider, SidebarInset } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Checkbox } from "@/components/ui/checkbox"

const studentColumns = [
  {
    id: "select",
    header: ({ table }) => (
      <Checkbox
        checked={table.getIsAllPageRowsSelected()}
        onCheckedChange={(value) => table.toggleAllPageRowsSelected(!!value)}
        aria-label="Select all"
      />
    ),
    cell: ({ row }) => (
      <Checkbox
        checked={row.getIsSelected()}
        onCheckedChange={(value) => row.toggleSelected(!!value)}
        aria-label="Select row"
      />
    ),
    enableSorting: false,
    enableHiding: false,
  },
  {
    accessorKey: "id",
    header: "ID",
  },
  {
    accessorKey: "name",
    header: "Name",
  },
  {
    accessorKey: "grade",
    header: "Grade",
  },
  {
    accessorKey: "class",
    header: "Class",
  },
  {
    accessorKey: "exam1",
    header: "Exam 1",
  },
  {
    accessorKey: "exam2",
    header: "Exam 2",
  },
  {
    accessorKey: "final",
    header: "Final",
  },
]

const data = [
  {
    id: "1",
    name: "John Smith",
    grade: "10th",
    class: "10A",
    exam1: 85,
    exam2: 78,
    final: 90,
  },
  {
    id: "2",
    name: "Emma Wilson",
    grade: "9th",
    class: "9B",
    exam1: 92,
    exam2: 88,
    final: 95,
  },
  {
    id: "3",
    name: "Michael Johnson",
    grade: "8th",
    class: "8C",
    exam1: 75,
    exam2: 82,
    final: 80,
  },
  {
    id: "4",
    name: "Sophia Lee",
    grade: "11th",
    class: "11A",
    exam1: 95,
    exam2: 91,
    final: 98,
  },
  {
    id: "5",
    name: "Daniel Brown",
    grade: "10th",
    class: "10B",
    exam1: 88,
    exam2: 85,
    final: 87,
  },
  {
    id: "6",
    name: "Olivia Davis",
    grade: "9th",
    class: "9A",
    exam1: 90,
    exam2: 87,
    final: 92,
  },
  {
    id: "7",
    name: "William Taylor",
    grade: "11th",
    class: "11B",
    exam1: 82,
    exam2: 88,
    final: 85,
  },
  {
    id: "8",
    name: "Ava Martinez",
    grade: "8th",
    class: "8A",
    exam1: 78,
    exam2: 80,
    final: 83,
  },
  {
    id: "9",
    name: "James Anderson",
    grade: "10th",
    class: "10C",
    exam1: 87,
    exam2: 84,
    final: 89,
  },
  {
    id: "10",
    name: "Isabella Thomas",
    grade: "9th",
    class: "9C",
    exam1: 93,
    exam2: 90,
    final: 94,
  }
]

export default function Grades() {
  const [selectedGrade, setSelectedGrade] = useState("")
  const [selectedClass, setSelectedClass] = useState("")

  const grades = ["8th", "9th", "10th", "11th"]
  const classes = ["A", "B", "C"]

  const filteredData = data.filter(student => {
    const matchesGrade = !selectedGrade || selectedGrade === "all" || student.grade === selectedGrade
    const matchesClass = !selectedClass || selectedClass === "all" || student.class.includes(selectedClass)
    return matchesGrade && matchesClass
  })

  return (
    <SidebarProvider>
      <div className="flex min-h-screen dark:bg-background">
        <AppSidebar className="border-r border-gray-300 shadow-[2px_0_10px_rgb(107,114,128)]" />
        <SidebarInset className="flex-1">
          <div className="flex flex-col bg-gray-200 h-full">
            <Header />
            <div className="p-4">
              <div className="flex items-center justify-between mb-6">
                <div>
                  <h1 className="text-2xl font-semibold">Student Grades</h1>
                  <p className="text-muted-foreground">
                    Manage and view student grades
                  </p>
                </div>
                <div className="flex gap-4">
                  <Select value={selectedGrade} onValueChange={setSelectedGrade}>
                    <SelectTrigger className="w-[180px]">
                      <SelectValue placeholder="Select grade" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Grades</SelectItem>
                      {grades.map(grade => (
                        <SelectItem key={grade} value={grade}>
                          {grade}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                  <Select value={selectedClass} onValueChange={setSelectedClass}>
                    <SelectTrigger className="w-[180px]">
                      <SelectValue placeholder="Select class" />
                    </SelectTrigger>
                    <SelectContent>
                      <SelectItem value="all">All Classes</SelectItem>
                      {classes.map(cls => (
                        <SelectItem key={cls} value={cls}>
                          Class {cls}
                        </SelectItem>
                      ))}
                    </SelectContent>
                  </Select>
                </div>
              </div>

              <DataTable columns={studentColumns} data={filteredData} />
            </div>
          </div>
        </SidebarInset>
      </div>
    </SidebarProvider>
  )
}