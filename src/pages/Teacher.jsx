"use client"
import { useState } from "react"
import { DataTable } from "@/components/teacher/data-table"
import { SidebarProvider, SidebarInset } from "@/components/ui/sidebar"
import Header from "@/layout/header"
import { AppSidebar } from "@/components/sidebar/app-sidebar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
// Add Checkbox import at the top
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
    accessorKey: "age",
    header: "Age",
  },
  {
    accessorKey: "class",
    header: "Class",
  },
]

const data = [
  {
    id: "1",
    name: "John Smith",
    grade: "10th",
    age: 15,
    class: "10A",
  },
  {
    id: "2",
    name: "Emma Wilson",
    grade: "9th",
    age: 14,
    class: "9B",
  },
  {
    id: "3",
    name: "Michael Johnson",
    grade: "8th",
    age: 13,
    class: "8C",
  },
  {
    id: "4",
    name: "Sophia Lee",
    grade: "11th",
    age: 16,
    class: "11A",
  },
  {
    id: "5",
    name: "Daniel Brown",
    grade: "10th",
    age: 15,
    class: "10B",
  },
]

export default function Teacher() {
  const [selectedGrade, setSelectedGrade] = useState("")
  const [selectedClass, setSelectedClass] = useState("")

  const grades = ["8th", "9th", "10th", "11th"]
  const classes = ["A", "B", "C"]

  const filteredData = data.filter(student => {
    const matchesGrade = !selectedGrade || student.grade === selectedGrade
    const matchesClass = !selectedClass || student.class.includes(selectedClass)
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
                  <h1 className="text-2xl font-semibold">Students</h1>
                  <p className="text-muted-foreground">
                    View and filter students by grade and class
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

