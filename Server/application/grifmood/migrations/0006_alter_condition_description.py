# Generated by Django 4.1.2 on 2023-04-16 17:51

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('grifmood', '0005_alter_condition_description'),
    ]

    operations = [
        migrations.AlterField(
            model_name='condition',
            name='description',
            field=models.CharField(blank=True, max_length=255),
        ),
    ]
