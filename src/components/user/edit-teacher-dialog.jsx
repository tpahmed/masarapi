"use client"

import { useEffect } from "react"
import { zodResolver } from "@hookform/resolvers/zod"
import { useForm } from "react-hook-form"
import * as z from "zod"

import { Button } from "@/components/ui/button"
import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from "@/components/ui/dialog"
import { Form, FormControl, FormField, FormItem, FormLabel } from "@/components/ui/form"
import { Input } from "@/components/ui/input"

const teacherFormSchema = z.object({
  name: z.string().min(2, "Name must be at least 2 characters"),
  email: z.string().email("Invalid email address"),
  subject: z.string().min(2, "Subject must be at least 2 characters"),
  experienceYears: z.string().refine((val) => !isNaN(Number(val)) && Number(val) >= 0, {
    message: "Experience years must be a positive number",
  }),
  qualification: z.string().min(2, "Qualification must be at least 2 characters"),
})

export function EditTeacherDialog({ open, onOpenChange, teacher, onSave }) {
  const form = useForm({
    resolver: zodResolver(teacherFormSchema),
    defaultValues: teacher,
  })

  useEffect(() => {
    if (teacher) {
      form.reset({
        name: teacher.name,
        email: teacher.email,
        subject: teacher.subject,
        experienceYears: teacher.experienceYears.toString(),
        qualification: teacher.qualification,
      })
    }
  }, [teacher, form])

  function onSubmit(values) {
    onSave({ ...values, id: teacher.id })
    onOpenChange(false)
  }

  return (
    <Dialog open={open} onOpenChange={onOpenChange}>
      <DialogContent className="sm:max-w-[550px] max-h-[80vh] overflow-y-auto border-gray-800 p-0">
        <DialogHeader className="relative px-6 py-4">
          <DialogTitle className="text-xl font-semibold">Edit Teacher</DialogTitle>
          <DialogDescription className="text-gray-400 text-sm">
            Edit teacher information. Click save when you&apos;re done.
          </DialogDescription>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="px-6 pb-6">
            <div className="space-y-3 py-2">
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Full Name</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Dr. John Smith"
                        {...field}
                        className="border h-9 border-gray-200 dark:border-gray-800 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Email</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="john.smith@school.com"
                        type="email"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="subject"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Subject</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Mathematics"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="experienceYears"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Experience (Years)</FormLabel>
                    <FormControl>
                      <Input
                        type="number"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="qualification"
                render={({ field }) => (
                  <FormItem className="grid grid-cols-[120px_1fr] items-center gap-2">
                    <FormLabel className="text-sm text-right">Qualification</FormLabel>
                    <FormControl>
                      <Input
                        placeholder="Ph.D. Mathematics"
                        {...field}
                        className="border border-gray-200 dark:border-gray-800 h-9 text-sm placeholder:text-gray-500"
                      />
                    </FormControl>
                  </FormItem>
                )}
              />
            </div>
            <div className="mt-4 flex justify-end py-4 bg-opacity-20">
              <Button
                type="submit"
                className="h-9 px-4 text-sm dark:bg-white dark:text-black hover:bg-gray-800 bg-black text-white dark:hover:bg-gray-200"
              >
                Save changes
              </Button>
            </div>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  )
}

